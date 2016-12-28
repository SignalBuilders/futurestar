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
import org.zhps.base.util.PropertiesUtil;
import org.zhps.df.entity.Quotation;
import org.zhps.df.task.TaskHelper;
import scala.Tuple2;

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
    static{
        TaskHelper.openMarket();
        TaskHelper.closeMarket();
    }
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("market").setMaster("local[*]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.milliseconds(200));
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
                return Arrays.asList(quotation).iterator();
            }
        });

        words.foreachRDD(new VoidFunction2<JavaRDD<Quotation>, Time>() {
            @Override
            public void call(JavaRDD<Quotation> quotationJavaRDD, Time time) throws Exception {
                quotationJavaRDD.foreach(new VoidFunction<Quotation>() {
                    @Override
                    public void call(Quotation quotation) throws Exception {
                        System.out.println(quotation);
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
