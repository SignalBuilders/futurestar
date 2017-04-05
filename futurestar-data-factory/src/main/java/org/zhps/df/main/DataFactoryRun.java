package org.zhps.df.main;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.hbase.async.*;
import org.hbase.async.Scanner;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.df.entity.Quotation;
import org.zhps.df.task.TaskHelper;
import redis.clients.jedis.Jedis;
import scala.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/23.
 */
public class DataFactoryRun {
    static{
        TaskHelper.openMarket();
        TaskHelper.closeMarket();
    }
    private static final Pattern SPACE = Pattern.compile("\\|");
    private static final byte[] TABLE_NAME = "quotation".getBytes();
    private static final byte[] COLUMN_FAMILY = "q".getBytes();
    private static final HBaseClient hBaseClient = BaseHbase.gethBaseClient();
    final static Jedis jedis = BaseRedis.getJedis();
//    final static String openMarket = jedis.get("open");


    public static void main(String[] args) throws IOException {
        SparkConf sparkConf = new SparkConf().setAppName("market").setMaster("local[*]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.milliseconds(PropertiesUtil.SPARK_DURATIONS));
        Set<String> topicSet = new HashSet<>(Arrays.asList(PropertiesUtil.MK_TOPIC.split(",")));
//        String[] topicArray = PropertiesUtil.MK_TOPIC.split(",");
//        for (String topic : topicArray) {
//            topicMap.put(topic, 3);
//        }
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", PropertiesUtil.BOOTSTRAP_SERVERS);
//        JavaPairReceiverInputDStream<String, String> messages =
//                KafkaUtils.createStream(jssc, PropertiesUtil.ZOOKEEPER_QUORUM, PropertiesUtil.CONSUMER_GROUP_ID, topicMap);
        JavaPairInputDStream<String, String> messages = KafkaUtils.createDirectStream(jssc,
                String.class,
                String.class,
                StringDecoder.class,
                StringDecoder.class,
                kafkaParams,
                topicSet);

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
                quotation.setLastPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LAST_PRICE]));
                quotation.setOpenPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_OPEN_PRICE]));
                quotation.setHighestPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_HIGHEST_PRICE]));
                quotation.setLowestPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LOWEST_PRICE]));
                quotation.setUpperLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_UPPERLIMIT_PRICE]));
                quotation.setLowerLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LOWERLIMIT_PRICE]));
                quotation.setUpdateTime(quoStr[PropertiesUtil.MK_QUO_UPDATETIME]);
                quotation.setTradingDay(quoStr[PropertiesUtil.MK_QUO_TRADINGDAY]);
                quotation.setVolume(Long.parseLong(quoStr[PropertiesUtil.MK_QUO_VOLUME]));
                quotation.setInterest(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_INTEREST]));
                return Arrays.asList(quotation).iterator();
            }
        });

        words.foreachRDD(new VoidFunction2<JavaRDD<Quotation>, Time>() {
            @Override
            public void call(JavaRDD<Quotation> quotationJavaRDD, Time time) throws Exception {
                quotationJavaRDD.foreach(new VoidFunction<Quotation>() {
                    @Override
                    public void call(Quotation quotation) throws Exception {
                        String updateTime = quotation.getUpdateTime();
                        int hour = Integer.parseInt(updateTime.split(":")[0]);
                        if((hour >= 21 && hour <= 23) || (hour >= 9 && hour <= 15)){//open time
                            dayLine(quotation);
                            t3mLine(quotation);
                            f5mLine(quotation);
                        }
                    }
                });
            }
        });

        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void dayLine(Quotation quotation){
        String openMarket = jedis.get("open");

        double lastPrice = quotation.getLastPrice();
        String[] openMarkets = SPACE.split(openMarket);
        double ave5dPrice = Math.round(lastPrice + Double.parseDouble(openMarkets[1])) / 5;
        double ave10dPrice = Math.round(lastPrice + Double.parseDouble(openMarkets[2])) / 10;

        String day = quotation.getTradingDay();
        String updateTime = quotation.getUpdateTime().split(":")[0];
        if(quotation.getUpdateTime().equalsIgnoreCase("14:59:59")){
            updateTime = "15";
        }
        String open = String.valueOf(quotation.getOpenPrice());
        String highest = String.valueOf(quotation.getHighestPrice());
        String lowest = String.valueOf(quotation.getLowestPrice());
        String last = String.valueOf(quotation.getLastPrice());
        String ave5d = String.valueOf(ave5dPrice);
        String ave10d = String.valueOf(ave10dPrice);
        String volume = String.valueOf(quotation.getVolume());
        String interest = String.valueOf(quotation.getInterest());

        StringBuilder value = new StringBuilder(day).append("|").append(updateTime).append("|")
                .append(open).append("|").append(highest).append("|").append(lowest).append("|")
                .append(last).append("|").append(ave5d).append("|").append(ave10d).append("|")
                .append(volume).append("|").append(interest);

        jedis.set("close", value.toString());
    }

    private static void t3mLine(Quotation quotation){
        String updateTime = quotation.getUpdateTime();
        int minute = Integer.parseInt(updateTime.split(":")[1]);
        int second = Integer.parseInt(updateTime.split(":")[2]);
        int mod = minute % 3;
        if(mod == 0){
            // TODO: 2017/4/5 save open to hbase,caculate ave15m, ave30m
        }

        if(mod + 2 == minute && second == 59){
            /// TODO: 2017/4/5 save close to hbase
        }
    }

    private static void f5mLine(Quotation quotation){
        String updateTime = quotation.getUpdateTime();
        int minute = Integer.parseInt(updateTime.split(":")[1]);
        int second = Integer.parseInt(updateTime.split(":")[2]);
        int mod = minute % 5;
        if(mod == 0){
            // TODO: 2017/4/5 save open to hbase,caculate ave15m, ave30m
        }

        if(mod + 4 == minute && second == 59){
            /// TODO: 2017/4/5 save close to hbase
        }
    }
}

//class JavaSparkSessionSingleton {
//    private static transient SparkSession instance = null;
//    public static SparkSession getInstance(SparkConf sparkConf) {
//        if (instance == null) {
//            instance = SparkSession
//                    .builder()
//                    .config(sparkConf)
//                    .getOrCreate();
//        }
//        return instance;
//    }
//}
