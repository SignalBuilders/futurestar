package org.zhps.df.main;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.df.entity.Quotation;
import org.zhps.df.task.TaskHelper;
import scala.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/23.
 */
public class BaseSparkStreaming {
//    static{
//        TaskHelper.openMarket();
//        TaskHelper.closeMarket();
//    }
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final byte[] TABLE_NAME = "quotation".getBytes();
    private static final byte[] COLUMN_FAMILY = "quo".getBytes();
    private static final HBaseClient hBaseClient = BaseHbase.gethBaseClient();


    public static void main(String[] args) throws IOException {
        SparkConf sparkConf = new SparkConf().setAppName("market").setMaster("local[*]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.milliseconds(100));
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topicArray = PropertiesUtil.MK_TOPIC.split(",");
        for (String topic : topicArray) {
            topicMap.put(topic, 3);
        }
        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(jssc, PropertiesUtil.ZOOKEEPER_QUORUM, PropertiesUtil.CONSUMER_GROUP_ID, topicMap);

        JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
            @Override
            public String call(Tuple2<String, String> tuple2) {
                return tuple2._2();
            }
        });

        JavaDStream<Quotation> words = lines.flatMap(new FlatMapFunction<String, Quotation>() {
            @Override
            public Iterator<Quotation> call(String x) {
                String[] quoStr = SPACE.split(x);
                Quotation quotation = new Quotation();
                quotation.setInstrumentId(quoStr[PropertiesUtil.MK_QUO_INSTRUMENTID]);
                quotation.setLastPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LASTPRICE]));
                quotation.setOpenPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_OPENPRICE]));
                quotation.setUpperLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_UPPERLIMITPRICE]));
                quotation.setLowerLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LOWERLIMITPRICE]));
                quotation.setUpdateTime(quoStr[PropertiesUtil.MK_QUO_UPDATETIME]);
                quotation.setTradingDay(quoStr[PropertiesUtil.MK_QUO_TRADINGDAY]);
                quotation.setUpdateMillisec(quoStr[PropertiesUtil.MK_QUO_UPDATETIMEMILLISEC]);
                return Arrays.asList(quotation).iterator();
            }
        });

        words.foreachRDD(new VoidFunction2<JavaRDD<Quotation>, Time>() {
            @Override
            public void call(JavaRDD<Quotation> quotationJavaRDD, Time time) throws Exception {
                quotationJavaRDD.foreach(new VoidFunction<Quotation>() {
                    @Override
                    public void call(Quotation quotation) throws Exception {
                        byte[] rowKey = (quotation.getTradingDay() + "|" + quotation.getInstrumentId() + "|"
                                + quotation.getUpdateTime() + "|" + quotation.getUpdateMillisec()).getBytes();
                        byte[] last = String.valueOf(quotation.getLastPrice()).getBytes();
                        byte[] open = String.valueOf(quotation.getOpenPrice()).getBytes();
                        byte[] upper = String.valueOf(quotation.getUpperLimitPrice()).getBytes();
                        byte[] lower = String.valueOf(quotation.getLowerLimitPrice()).getBytes();
                        byte[][] colBytes = {"last".getBytes(),"open".getBytes(),"upper".getBytes(),"lower".getBytes()};
                        byte[][] valBytes = {last,open,upper,lower};

                        PutRequest put = new PutRequest(TABLE_NAME, rowKey, COLUMN_FAMILY, colBytes, valBytes);
                        put.setDurable(false);
                        hBaseClient.put(put);
                    }
                });
            }
        });


//        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
//                new PairFunction<String, String, Integer>() {
//                    @Override
//                    public Tuple2<String, Integer> call(String s) {
//                        return new Tuple2<>(s, 1);
//                    }
//                }).reduceByKey(new Function2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer i1, Integer i2) {
//                return i1 + i2;
//            }
//        });
//
//        wordCounts.print();

        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/** Lazily instantiated singleton instance of SparkSession */
class JavaSparkSessionSingleton {
    private static transient SparkSession instance = null;
    public static SparkSession getInstance(SparkConf sparkConf) {
        if (instance == null) {
            instance = SparkSession
                    .builder()
                    .config(sparkConf)
                    .getOrCreate();
        }
        return instance;
    }
}
