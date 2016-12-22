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
        props.put(PropertiesUtil.BOOTSTRAP_SERVERS_NAME, PropertiesUtil.BOOTSTRAP_SERVERS);
        props.put(PropertiesUtil.ACKS_NAME, PropertiesUtil.ACKS);
        props.put(PropertiesUtil.RETRIES_NAME, PropertiesUtil.RETRIES);
        props.put(PropertiesUtil.BATCH_SIZE_NAME, PropertiesUtil.BATCH_SIZE);
        props.put(PropertiesUtil.LINGER_MS_NAME, PropertiesUtil.LINGER_MS);
        props.put(PropertiesUtil.BUFFER_MEMORY_NAME, PropertiesUtil.BUFFER_MEMORY);
        props.put(PropertiesUtil.KEY_SERIALIZER_NAME, PropertiesUtil.KEY_SERIALIZER);
        props.put(PropertiesUtil.VALUE_SERIALIZER_NAME, PropertiesUtil.VALUE_SERIALIZER);
        producer = new KafkaProducer<String, String>(props);
    }

    protected abstract void send(String topic, String value);

    protected abstract void send(String topic, String key, String value);
}
