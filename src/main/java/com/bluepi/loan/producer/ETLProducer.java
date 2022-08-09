package com.bluepi.loan.producer;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ETLProducer {
    private KafkaProducer<String, String> producer;
    private static volatile ETLProducer instance;

    private ETLProducer() {
        Properties props = new Properties();
        props.put(IntegratorConstants.KAFKA_SERVER_KEY, IntegratorConstants.KAFKA_SERVER_VALUE);
        props.put(IntegratorConstants.KAFKA_KEY_SERIALIZER_KEY, IntegratorConstants.KAFKA_KEY_SERIALIZER_VALUE);
        props.put(IntegratorConstants.KAFKA_VALUE_SERIALIZER_KEY, IntegratorConstants.KAFKA_VALUE_SERIALIZER_VALUE);
        producer = new KafkaProducer<>(props);
    }

    public synchronized static ETLProducer getInstance() {

        if (instance == null) {
            synchronized (ETLProducer.class) {
                if (instance == null)
                    instance = new ETLProducer();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected ETLProducer readResolve() {
        return getInstance();
    }

    public void sendMessage(@KafkaKey String key, MessagePayload messagePayload, String topicName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProducerRecord<String, String> record=new ProducerRecord<>(topicName,key, objectMapper.writeValueAsString(messagePayload));
            producer.send(record);
        }catch (Exception e){
            throw e;
        }
    }

    public void sendMessage(@KafkaKey String key, String messagePayload, String topicName)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProducerRecord<String, String> record=new ProducerRecord<>(topicName, key,messagePayload);
            producer.send(record);
        }catch (Exception e){
            throw e;
        }
    }
}
