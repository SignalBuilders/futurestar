package org.zhps.market.util;

import org.apache.hadoop.hdfs.server.namenode.Quota;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.market.entity.Quotation;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/3/27.
 */
public class ReadHistoryQuotation {
    public static void main(String[] args) {
//        List<Quotation> quotations = readFromTxt("D:\\work\\doc\\market\\history\\FutureDataHistory_2016.txt", "fg");
        List<Quotation> quotations = readFromXml("D:\\work\\doc\\market\\history\\MarketData_Year_2015.xls", "rb");
        calculateAve5d10d(quotations);
        writeToHbase(quotations);
        for(Quotation quotation : quotations){
            System.out.println(quotation);
        }
//        System.out.println("ma|" + (100000000 - Integer.parseInt("20160104")) + "|20160104");
//        System.out.println("ma|" + (100000000 - Integer.parseInt("20161230")) + "|20161230");
    }

    private static List<Quotation> readFromTxt(String path, String variety){
        BufferedReader bufferedReader = null;
        List<Quotation> quotations = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path)));
            String line = null;
            String strs[] = null;
            String traday = "";
            int interest = 0;
            Quotation quotation = null;
            while((line = bufferedReader.readLine()) != null){
                if(line.startsWith("201")){
                    strs = line.split("\\|");
                    if(!traday.equalsIgnoreCase(strs[0].trim())){
                        quotation = new Quotation();
                        quotations.add(quotation);
                        interest = 0;
                    }
                    if(strs[1].trim().toLowerCase().startsWith(variety.toLowerCase())){
                        if(Integer.parseInt(strs[11].trim().replaceAll(",", "")) > interest){
                            quotation.setInstrumentId(strs[1].trim());
                            quotation.setOpenPrice(Double.parseDouble(strs[3].trim().replaceAll(",", "")));
                            quotation.setHighestPrice(Double.parseDouble(strs[4].trim().replaceAll(",", "")));
                            quotation.setLowestPrice(Double.parseDouble(strs[5].trim().replaceAll(",", "")));
                            quotation.setLatestPrice(Double.parseDouble(strs[6].trim().replaceAll(",", "")));
                            quotation.setTradingDay(strs[0].replaceAll("-", ""));
                            quotation.setVolume(Long.parseLong(strs[10].trim().replaceAll(",", "")));
                            quotation.setInterest((long)Double.parseDouble(strs[11].trim().replaceAll(",", "")));
                            interest = Integer.parseInt(strs[11].trim().replaceAll(",", ""));
                        }
                    }
                    traday = strs[0];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotations;
    }

    private static List<Quotation> readFromXml(String path, String variety){
        File file = new File(path);
        List<Quotation> quotationList = new ArrayList<Quotation>();
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            int rowStart = hssfSheet.getFirstRowNum() + 3;
            int rowEnd = hssfSheet.getLastRowNum() - 4;
            String type = "";
            boolean exec = false;
            for(int i = rowStart; i < rowEnd; i++){
                exec = false;
                HSSFRow hssfRow = hssfSheet.getRow(i);
                String id = hssfRow.getCell(0).getStringCellValue();
                String traday = hssfRow.getCell(1).getStringCellValue();
                double open = hssfRow.getCell(4).getNumericCellValue();
                double highest = hssfRow.getCell(5).getNumericCellValue();
                double lowest = hssfRow.getCell(6).getNumericCellValue();
                double last = hssfRow.getCell(7).getNumericCellValue();
                int volume = (int) hssfRow.getCell(11).getNumericCellValue();//Integer.parseInt(hssfRow.getCell(11).getNumericCellValue());
                int interest = (int) hssfRow.getCell(13).getNumericCellValue();
                if(!id.equalsIgnoreCase("")){
                    type = id;
                }
                if(type.startsWith(variety)){
                    for(Quotation quotation : quotationList){
                        if(quotation.getTradingDay().equalsIgnoreCase(traday)){
                            if(interest > quotation.getInterest()){
                                quotation.setInstrumentId(type);
                                quotation.setOpenPrice(open);
                                quotation.setHighestPrice(highest);
                                quotation.setLatestPrice(last);
                                quotation.setLowestPrice(lowest);
                                quotation.setVolume(volume);
                                quotation.setInterest(interest);
                            }
                            exec = true;
                        }
                    }
                    if(!exec){
                        Quotation quotation = new Quotation();
                        quotation.setInstrumentId(type);
                        quotation.setTradingDay(traday);
                        quotation.setOpenPrice(open);
                        quotation.setHighestPrice(highest);
                        quotation.setLatestPrice(last);
                        quotation.setLowestPrice(lowest);
                        quotation.setVolume(volume);
                        quotation.setInterest(interest);
                        quotationList.add(quotation);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotationList;
    }

    private static void calculateAve5d10d(List<Quotation> quotations){
        Queue<Double> ave5d = new ArrayDeque<Double>();
        Queue<Double> ave10d = new ArrayDeque<Double>();
        for(Quotation quotation : quotations){
            ave5d.add(quotation.getLatestPrice());
            ave10d.add(quotation.getLatestPrice());
            if(ave5d.size() == 5){
                double sum = 0;
                for(double value : ave5d){
                    sum += value;
                    quotation.setAve5d((long)(sum / 5));
                }
                ave5d.poll();
            }
            if(ave10d.size() == 10){
                double sum = 0;
                for(double value : ave10d){
                    sum += value;
                    quotation.setAve10d((long)(sum / 10));
                }
                ave10d.poll();
            }
        }
    }

    private static void writeToHbase(List<Quotation> quotations){
        HBaseClient hBaseClient = BaseHbase.gethBaseClient();
        for(Quotation quotation : quotations){
            String instrument = quotation.getInstrumentId().toLowerCase();
            String open = String.valueOf(quotation.getOpenPrice());
            String highest = String.valueOf(quotation.getHighestPrice());
            String lowest = String.valueOf(quotation.getLowestPrice());
            String last = String.valueOf(quotation.getLatestPrice());
            String ave5d = String.valueOf(quotation.getAve5d());
            String ave10d = String.valueOf(quotation.getAve10d());
            String volume = String.valueOf(quotation.getVolume());
            String interest = String.valueOf(quotation.getInterest());
            String day = quotation.getTradingDay();
            byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes()
                    ,"ave5d".getBytes(),"ave10d".getBytes(),"volume".getBytes(),"interest".getBytes()};
            byte[][] valBytes = {open.getBytes(),highest.getBytes(),lowest.getBytes(),last.getBytes()
                    , ave5d.getBytes(), ave10d.getBytes(), volume.getBytes(), interest.getBytes()};
            PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), (instrument + "|" + (100000000 - Integer.parseInt(day)) + "|" + day).getBytes(),
                    PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
            hBaseClient.put(put);
        }

    }
}
