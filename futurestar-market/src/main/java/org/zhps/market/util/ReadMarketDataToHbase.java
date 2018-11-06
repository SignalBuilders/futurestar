package org.zhps.market.util;

import org.apache.commons.io.IOUtils;
import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2018/7/7.
 */
public class ReadMarketDataToHbase {
        private final static String QUO = "rb10";

        public static void main(String[] args) {
            final String filePath = "D:\\doc\\futures\\" + QUO + "-01.txt";
            BufferedReader br = null;
            HBaseClient hbaseClient = BaseHbase.gethBaseClient();
            try {
                br = new BufferedReader(new FileReader(new File(filePath)));
                String value = null;
                while((value = br.readLine()) != null && !value.equalsIgnoreCase("")){
                    String[] arr = value.split(",");
//                    System.out.println("date: " + arr[0] + " open: " + arr[1] + " highest: " + arr[2] +
//                            " lowest: " + arr[3] + " last: " + arr[4] + "ave5d: " + arr[5] + " ave10d: " + arr[6]);
                    byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes()
                            ,"ave5d".getBytes(),"ave10d".getBytes(),"volume".getBytes(),"interest".getBytes()};
                    byte[][] valBytes = {arr[0].getBytes(), arr[1].getBytes(), arr[2].getBytes(), arr[3].getBytes(),
                            arr[4].getBytes(), arr[5].getBytes(), arr[6].getBytes(), arr[7].getBytes()};
                    PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), (QUO + "|" + (100000000 - Integer.parseInt(arr[8])) + "|" + arr[8]).getBytes(),
                            PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
                    hbaseClient.put(put);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.closeQuietly(br);
            }
        }
}
