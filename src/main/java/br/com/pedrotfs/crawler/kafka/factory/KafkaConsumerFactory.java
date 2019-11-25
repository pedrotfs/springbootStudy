package br.com.pedrotfs.crawler.kafka.factory;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaConsumerFactory {

    @Value("${default.bootstrap.server}")
    private String bootstrapServer;

    @Value("${kafka.topic.consume}")
    private String topic;

    @Value("${kafka.consumer.group}")
    private String group;

    @Value("${kafka.consumer.offset.reset}")
    private String offsetReset;

    @Autowired
    private KafkaFactoryUtils kafkaFactoryUtils;

    public KafkaConsumer<String, String> createConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(createPropertiesConsumer());
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    private Properties createPropertiesConsumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        kafkaFactoryUtils.setSerializerPropertiesConsumer(properties);
        return properties;
    }
}