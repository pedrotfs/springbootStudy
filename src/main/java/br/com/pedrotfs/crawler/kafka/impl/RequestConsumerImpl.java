package br.com.pedrotfs.crawler.kafka.impl;

import br.com.pedrotfs.crawler.kafka.RequestConsumer;
import br.com.pedrotfs.crawler.kafka.factory.KafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RequestConsumerImpl implements RequestConsumer {

    @Autowired
    private KafkaConsumerFactory consumerFactory;

    private KafkaConsumer<String, String> consumer = null;

    private static Logger LOG = LoggerFactory.getLogger(RequestConsumerImpl.class);

    private String result;

    @Override
    public String consumeFeed() {
        result = "";
        if(consumer == null)
        {
            consumer = consumerFactory.createConsumer();
        }
        LOG.info("pooling kafka queue.");
        final ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(1000));
        if(poll.isEmpty()) {
            LOG.info("No records recovered");
        } else {
            poll.forEach(r -> {
                LOG.info("request pooled. Key: " + r.key() + ", timestamp: " +
                        r.timestamp() + ", offset: " + r.offset() + ", value: " + r.value());
                setResult(r.value());
            });
        }
        return result;
    }

    private void setResult(final String value) {
        result = value;
    }
}
