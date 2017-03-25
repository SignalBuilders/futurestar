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
public class BaseSparkStreaming {
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
//                        byte[] rowKey = (quotation.getInstrumentId() + "|" + quotation.getTradingDay() + "|"
//                                + quotation.getUpdateTime() + "|" + quotation.getUpdateMillisec()).getBytes();
//                        byte[] last = String.valueOf(quotation.getLastPrice()).getBytes();
//                        byte[] open = String.valueOf(quotation.getOpenPrice()).getBytes();
//                        byte[] upper = String.valueOf(quotation.getUpperLimitPrice()).getBytes();
//                        byte[] lower = String.valueOf(quotation.getLowerLimitPrice()).getBytes();
//                        byte[][] colBytes = {"last".getBytes(),"open".getBytes(),"upper".getBytes(),"lower".getBytes()};
//                        byte[][] valBytes = {last,open,upper,lower};

//                        PutRequest put = new PutRequest(TABLE_NAME, rowKey, COLUMN_FAMILY, colBytes, valBytes);
//                        put.setDurable(false);
//                        hBaseClient.put(put);
//                        Quotation{instrumentId='RM', lastPrice=2444.0, openPrice=2453.0, highestPrice=2457.0, lowestPrice=2427.0,
//                                upperLimitPrice=2571.0, lowerLimitPrice=2325.0, updateTime='07:53:54', tradingDay='20170303', updateMillisec='500'}

//                        System.out.println(quotation);

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
                });
            }
        });

        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//        byte[][] colBytes = {"last".getBytes(),"open".getBytes(),"upper".getBytes(),"lower".getBytes()};
//        byte[][] valBytes = {"6877".getBytes(),"6954".getBytes(),"7448".getBytes(),"6472".getBytes()};
//        PutRequest put = new PutRequest(TABLE_NAME, "SR|20170204|11:16:40|500".getBytes(), COLUMN_FAMILY, colBytes, valBytes);
//        put.setDurable(false);
//        hBaseClient.put(put);
//        BinaryComparator bc = new BinaryComparator("20170203".getBytes());
//        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, bc);
//        GetRequest get = new GetRequest("traday", "tdrow".getBytes());
//        get.setFilter(rowFilter);
//        try {
//            ArrayList<KeyValue> al = hBaseClient.get(get).join();
//            System.out.println(al.size());
//            for(int i = al.size() - 1; i >= al.size() - 5; i--){
//                System.out.println(new String(al.get(i).qualifier()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Scanner scanner = hBaseClient.newScanner("traday");
////        scanner.setStartKey("SR|20170203|11:16:40");
//        scanner.setFilter(rowFilter);
//        scanner.setMaxNumRows(9);
//        try {
//            ArrayList<ArrayList<KeyValue>> al = scanner.nextRows().join();
//            for(ArrayList<KeyValue> kv : al){
//                System.out.println(kv);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
