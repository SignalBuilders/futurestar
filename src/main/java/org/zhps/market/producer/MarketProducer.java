package org.zhps.market.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.zhps.kafka.BaseProducer;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/21.
 */
public class MarketProducer extends BaseProducer{

    @Override
    public void send(String topic, String value) {
        ProducerRecord<String, String> record = new ProducerRecord(topic, value);
        producer.send(record);
    }

    @Override
    public void send(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord(topic, key, value);
        producer.send(record);
    }
}
