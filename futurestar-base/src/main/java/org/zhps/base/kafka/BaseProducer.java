package org.zhps.base.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.zhps.base.util.PropertiesUtil;

import java.util.Properties;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/21.
 */
public abstract class BaseProducer {
    protected static Producer<String, String> producer;
    static{
        Properties props = new Properties();
        props.put("bootstrap.servers", PropertiesUtil.BOOTSTRAP_SERVERS);
        props.put("acks", PropertiesUtil.ACKS);
        props.put("retries", PropertiesUtil.RETRIES);
        props.put("batch.size", PropertiesUtil.BATCH_SIZE);
        props.put("linger.ms", PropertiesUtil.LINGER_MS);
        props.put("buffer.memory", PropertiesUtil.BUFFER_MEMORY);
        props.put("key.serializer", PropertiesUtil.KEY_SERIALIZER);
        props.put("value.serializer", PropertiesUtil.VALUE_SERIALIZER);
        producer = new KafkaProducer<String, String>(props);
    }

    protected abstract void send(String topic, String value);

    protected abstract void send(String topic, String key, String value);
}
