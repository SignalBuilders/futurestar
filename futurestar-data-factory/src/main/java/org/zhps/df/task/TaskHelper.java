package org.zhps.df.task;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;
import org.hbase.async.*;
import org.hbase.async.Scanner;
import org.hbase.async.generated.FilterPB;
import org.mortbay.util.ajax.JSON;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.base.util.StringUtil;
import org.zhps.df.util.ContractUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import scala.Int;

import java.util.*;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class TaskHelper {

    final static HBaseClient hbaseClient = BaseHbase.gethBaseClient();
    final static Jedis jedis = BaseRedis.getJedis();
//    static Calendar calendar = Calendar.getInstance();
    static String variety = PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RB];

    /**
     * open market init
     */
    public static void openMarket(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date curDate = new Date();
                System.out.println(curDate + " open market task start.........");
                //reset contracts in redis
                resetContracts();
                String value = null;

                for(String contract : ContractUtil.getContractGroup(variety)){
                    double sum5d = 0;
                    double sum10d = 0;
                    double ave5d = 0;
                    double ave10d = 0;
                    Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE);
                    scanner.setKeyRegexp(contract.substring(0, 2) + contract.substring(4));
//                    scanner.clearFilter();
//                    ScanFilter sf = new KeyRegexpFilter(contract.substring(0, 2) + contract.substring(4));
//                    scanner.setFilter(sf);
                    try {
                        ArrayList<ArrayList<KeyValue>> tds = scanner.nextRows(9).joinUninterruptibly();
                        if(tds != null && tds.size() != 0){
                            for(int i = 0; i < tds.size(); i++){
                                for(KeyValue c : tds.get(i)){
                                    if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave5d")){
                                        ave5d = Double.parseDouble(new String(c.value()));
                                    }
                                    if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave10d")){
                                        ave10d = Double.parseDouble(new String(c.value()));
                                    }
                                    if(new String(c.qualifier()).equalsIgnoreCase("last")){
                                        if(i < PropertiesUtil.MK_AVE_5D && tds.size() > PropertiesUtil.MK_AVE_5D){
                                            sum5d += Double.parseDouble(new String(c.value()));
                                        }
                                        if(tds.size() == PropertiesUtil.MK_AVE_10D){
                                            sum10d += Double.parseDouble(new String(c.value()));
                                        }
                                    }
                                }
                            }
//                    System.out.println("sum5d: " + sum5d + " sum10d: " + sum10d + " ave5d: " + ave5d + " ave10d: " + ave10d);
                            value = StringUtil.assembleString(jedis.get("traday"), "|", String.valueOf(sum5d),
                                    "|", String.valueOf(sum10d), "|", String.valueOf(ave5d), "|", String.valueOf(ave10d));
                        }else{
                            value = StringUtil.assembleString(jedis.get("traday"), "|0|0|0|0");
                        }
                        jedis.set(StringUtil.assembleString(contract, "_open"), value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(scanner != null){
                            scanner.close();
                        }
                    }
                }
            }
        };
        BaseTask.startTask(task, PropertiesUtil.OPEN_MARKET_DATE, PropertiesUtil.HOURS_12);
    }

    /**
     * close market
     */
    public static void closeMarket(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date curDate = new Date();
                System.out.println(curDate + " close market task start.........");
                for(String contract : ContractUtil.getContractGroup(variety)){
                    String value = jedis.get(StringUtil.assembleString(contract, "_close"));
                    if(value != null){
                        String[] values = value.split("\\|");
                        String day = values[0];
                        String time = values[1];
                        String last = values[2];
                        String open = values[3];
                        String highest = values[4];
                        String lowest = values[5];
                        String ave5d = values[6];
                        String ave10d = values[7];
                        String volume = values[8];
                        String interest = values[9];
                        byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes()
                                ,"ave5d".getBytes(),"ave10d".getBytes(),"volume".getBytes(),"interest".getBytes()};
                        byte[][] valBytes = {open.getBytes(),highest.getBytes(),lowest.getBytes(),last.getBytes()
                                , ave5d.getBytes(), ave10d.getBytes(), volume.getBytes(), interest.getBytes()};
                        System.out.println("time: " + time);
                        if(time.equalsIgnoreCase("15")){
                            contract = contract.substring(0, 2) + contract.substring(4);//rb1810->rb10
                            System.out.println(curDate + " write to hbase.........");
                            PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(),
                                    (StringUtil.assembleString(contract, "|",String.valueOf(100000000 - Integer.parseInt(day)), "|", day)).getBytes(),
                                    PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
                            hbaseClient.put(put);
                        }
                    }
                }
            }
        };
        BaseTask.startTask(task, PropertiesUtil.CLOSE_MARKET_DATE, PropertiesUtil.HOURS_24);
    }

    /**
     * reset contract in redis
     */
    private static void resetContracts(){
        System.out.println(new Date() + " reset contracts start.........");
        Calendar calendar = Calendar.getInstance();
        String variety = PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RB];
        String newContracts = "";

        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        month = month.length() == 1 ? "0" + month : month;
        String thisMonthContract = StringUtil.assembleString(variety, year, month);

        String[] historyContractArray = ContractUtil.getContractGroup(variety);
        for(String contract : historyContractArray){
            if(contract.compareTo(thisMonthContract) >= 0){
                newContracts += StringUtil.assembleString(contract, ",");
            }
        }

        if(month.equalsIgnoreCase("01") || month.equalsIgnoreCase("05") || month.equalsIgnoreCase("10") ){
            year = String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2);
            String newYearContract = StringUtil.assembleString(variety, year, month);
            if(!newContracts.contains(newYearContract)){
                newContracts += StringUtil.assembleString(newYearContract, ",");
            }
        }

        System.out.println(StringUtil.assembleString("contracts: ", newContracts.substring(0, newContracts.length() - 1)));
        jedis.set(variety, newContracts.substring(0, newContracts.length() - 1));
    }

    public static void main(String[] args) {
//        resetContracts();
//        openMarket();
//        closeMarket();
        HBaseClient hbaseClient = BaseHbase.gethBaseClient();
        byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes()
                ,"ave5d".getBytes(),"ave10d".getBytes(),"volume".getBytes(),"interest".getBytes()};
//        byte[][] colBytes = {"ave5d".getBytes(),"ave10d".getBytes()};
        byte[][] valBytes = {"3663".getBytes(), "3690".getBytes(), "3616".getBytes(), "3657".getBytes(),
                "3662".getBytes(), "3600".getBytes(), "4812000".getBytes(),"2945000".getBytes()};
//        byte[][] valBytes = {"3805".getBytes(), "4058".getBytes()};
        PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("rb10|79819493|20180507").getBytes(),
                PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
        hbaseClient.put(put);

//        System.out.println(100000000 - 20180502);

//        DeleteRequest delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("'rb01|79828774|20171226").getBytes(),
//                PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes());
//        hbaseClient.delete(delete);

//        System.out.println("rb|" + (100000000 - 20120101) + "|20120101");
//        System.out.println("rb|" + (100000000 - 20121231) + "|20121231");
//        System.out.println(100000000 - 20150101);
//
//
//        byte[][] valBytes1 = {"2290".getBytes(), "2295".getBytes(), "2306".getBytes()};
//        put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("rm|" + (100000000 - 20170109)).getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes, valBytes1);
//        hbaseClient.put(put);
//
//        DeleteRequest delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), "20170109".getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//        hbaseClient.delete(delete);
//        delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), "20170109".getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//        hbaseClient.delete(delete);

//        Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes());
//        String key = null;
//        String ave10d = null;
//        String ave5d = null;
//        String last = null;
//        String[] keySp = null;
//        try {
//            ArrayList<ArrayList<KeyValue>> rows = scanner.nextRows().join();
//            for(ArrayList<KeyValue> kvs : rows){
//                for(KeyValue kv : kvs){
//                    if(new String(kv.qualifier()).equalsIgnoreCase("ave10d")){
//                        ave10d = new String(kv.value());
//                        key = new String(kv.key());
//                    }
//                    if(new String(kv.qualifier()).equalsIgnoreCase("ave5d")){
//                        ave5d = new String(kv.value());
//                    }
//                    if(new String(kv.qualifier()).equalsIgnoreCase("last")){
//                        last = new String(kv.value());
//                    }
////                    System.out.println(new String(kv.key()) + "--" + new String(kv.qualifier()) + "--" + new String(kv.value()));
//                }
////                System.out.println("key: " + key + " ave10d: " + ave10d + " ave5d: " + ave5d + " last: " + last);
//                keySp = key.split("\\|");
////                key = keySp[0] + "|" + keySp[1] + "|" + (100000000 - Long.valueOf(keySp[1]));
////                byte[][] valBytes = {last.getBytes(), ave5d.getBytes(), ave10d.getBytes()};
////                PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), key.getBytes(),
////                        PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
////                hbaseClient.put(put);
//                key = keySp[0] + "|" + keySp[1];
//                DeleteRequest delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), key.getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//                hbaseClient.delete(delete);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //get value
//        HBaseClient hBaseClient = BaseHbase.gethBaseClient();
//        Scanner scanner = hBaseClient.newScanner("close");
//        scanner.setStartKey("rb|79828769|20171231");
//        scanner.setStopKey("rb|79839899|20160101");
//        ArrayList<ArrayList<KeyValue>> datas = null;
//        try {
//            datas = scanner.nextRows(10000).joinUninterruptibly();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String lastKey = "";
//        if(datas != null){
//            for(List<KeyValue> rows : datas){
//                for(KeyValue keyValue : rows){
//                    String key = new String(keyValue.key());
//                    if(!key.equalsIgnoreCase(lastKey)){
//                        System.out.println(new String(keyValue.key()));
//                        lastKey = key;
//                    }
//                }
//            }
//        }

//        while(true){
//            final Jedis jedis = BaseRedis.getJedis();
//            System.out.println(jedis.get("rb"));
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            new Thread(){
//                @Override
//                public void run() {
//                    System.out.println(jedis.get("rb"));
//                }
//            }.start();
//        }
//        System.out.println(jedis.get("rb1801_close"));

//        System.out.println("rb1801|" + (100000000 - 20170926) + "|20170926");

//        for(String contract : ContractUtil.getContractGroup(variety)) {
//            String value = jedis.get(StringUtil.assembleString(contract, "_close"));
//            System.out.println(contract + " " + value);
//        }
//        for(String contract : ContractUtil.getContractGroup(variety)) {
//            double ave5d = 0;
//            double ave10d = 0;
//            double sum5d = 0;
//            double sum10d = 0;
//            Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE);
////            System.out.println(contract);
//            try {
//                scanner.setKeyRegexp(contract);
//                ArrayList<ArrayList<KeyValue>> tds = scanner.nextRows(9).join();
//                if(tds != null){
//                    for(int i = 0; i < tds.size(); i++){
//                        for(KeyValue c : tds.get(i)){
//                            if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave5d")){
//                                ave5d = Double.parseDouble(new String(c.value()));
//                            }
//                            if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave10d")){
//                                ave10d = Double.parseDouble(new String(c.value()));
//                            }
//                            if(new String(c.qualifier()).equalsIgnoreCase("last")){
//                                if(i < PropertiesUtil.MK_AVE_5D && tds.size() > PropertiesUtil.MK_AVE_5D){
//                                    sum5d += Double.parseDouble(new String(c.value()));
//                                }
//                                if(tds.size() == PropertiesUtil.MK_AVE_10D){
//                                    sum10d += Double.parseDouble(new String(c.value()));
//                                }
//                            }
//                        }
//                    }
//                    System.out.println(tds.size());
//                    System.out.println(sum5d);
////                    System.out.println(sum10d);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                if(scanner != null){
//                    try {
//                        scanner.close().joinUninterruptibly();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        String contract = "rb2205";
//        contract = contract.substring(0, 2) + contract.substring(4);
//        System.out.println(contract);
    }
}
