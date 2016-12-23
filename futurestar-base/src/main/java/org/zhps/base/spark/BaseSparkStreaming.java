package org.zhps.base.spark;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.zhps.base.util.PropertiesUtil;
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
    private static final Pattern SPACE = Pattern.compile(" ");

    public static JavaPairReceiverInputDStream fetchInputDStream(String appName, String topics, int threadNums){
        SparkConf sparkConf = new SparkConf().setAppName(appName).setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.milliseconds(200));
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topicArray = topics.split(",");
        for (String topic: topicArray) {
            topicMap.put(topic, threadNums);
        }
        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(jssc, PropertiesUtil.ZOOKEEPER_QUORUM, PropertiesUtil.CONSUMER_GROUP_ID, topicMap);
        return messages;
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                SparkConf sparkConf = new SparkConf().setAppName("market").setMaster("local[2]");
                JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.milliseconds(2000));
                Map<String, Integer> topicMap = new HashMap<>();
                String[] topicArray = "forward-quotation".split(",");
                for (String topic: topicArray) {
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

                JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
                    @Override
                    public Iterator<String> call(String x) {
                        return Arrays.asList(SPACE.split(x)).iterator();
                    }
                });

                JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
                        new PairFunction<String, String, Integer>() {
                            @Override
                            public Tuple2<String, Integer> call(String s) {
                                return new Tuple2<>(s, 1);
                            }
                        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer i1, Integer i2) {
                        return i1 + i2;
                    }
                });

                wordCounts.print();

                jssc.start();
                try {
                    jssc.awaitTermination();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
