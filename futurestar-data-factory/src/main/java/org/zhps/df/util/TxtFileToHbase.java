package org.zhps.df.util;

import com.stumbleupon.async.Callback;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.columnar.BOOLEAN;
import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.df.entity.Quotation;
import scala.Function1;
import scala.runtime.BoxedUnit;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/1/1.
 */
public class TxtFileToHbase {
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final byte[] TABLE_NAME = "quotation".getBytes();
    private static final byte[] COLUMN_FAMILY = "quo".getBytes();
    private static final HBaseClient hBaseClient = BaseHbase.gethBaseClient();

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder().master("local[*]")//.master("spark://192.168.1.26:7077")
                .appName("txtFileToHbase")
                .getOrCreate();

        JavaRDD<Quotation> quotationRDD = spark.read()
                .textFile("D:\\market\\market-20161219.txt")
                .javaRDD()
                .map(new Function<String, Quotation>() {
            @Override
            public Quotation call(String s) throws Exception {
                Quotation quo = new Quotation();
                if(!s.trim().equals("")){
                    String[] values = SPACE.split(s);
                    quo.setInstrumentId(values[PropertiesUtil.MK_QUO_INSTRUMENTID]);
                    quo.setLastPrice(Double.parseDouble(values[PropertiesUtil.MK_QUO_LAST_PRICE]));
                    quo.setUpdateTime(values[2]);
                    quo.setTradingDay("20161219");
                }
                return quo;
            }
        });
        Dataset<Row> quotationDf = spark.createDataFrame(quotationRDD, Quotation.class);

//        quotationDf.createOrReplaceTempView("quotationTable");

//        Dataset<Row> orderQuoDf = quotationDf.orderBy("updateTime");
//        quotationDf.show();
        quotationDf.foreach(new ForeachFunction<Row>() {
            @Override
            public void call(Row row) throws Exception {
                byte[] rowKey = (row.getString(4) + "|" + row.getString(0) + "|" + row.getString(5)).getBytes();
                byte[] last = String.valueOf(row.getDouble(1)).getBytes();
                byte[][] colBytes = {"last".getBytes(),"open".getBytes(),"upper".getBytes(),"lower".getBytes()};
                byte[][] valBytes = {last,"0".getBytes(),"0".getBytes(),"0".getBytes()};
                PutRequest put = new PutRequest(TABLE_NAME, rowKey, COLUMN_FAMILY, colBytes, valBytes);
                hBaseClient.put(put).addCallback(new InsertResponse()).join();
            }
        });
        try {
            hBaseClient.shutdown().joinUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

final class InsertResponse implements Callback<String, Object>{
    @Override
    public String call(Object o) throws Exception {
        System.out.println(o);
        return "";
    }
}
