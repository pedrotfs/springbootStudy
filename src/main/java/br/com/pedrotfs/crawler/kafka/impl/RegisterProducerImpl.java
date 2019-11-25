package br.com.pedrotfs.crawler.kafka.impl;

import br.com.pedrotfs.crawler.domain.LtfGame;
import br.com.pedrotfs.crawler.kafka.RegisterProducer;
import br.com.pedrotfs.crawler.kafka.callback.ProducerCallBack;
import br.com.pedrotfs.crawler.kafka.factory.KafkaProducerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterProducerImpl implements RegisterProducer {

    private static Logger LOG = LoggerFactory.getLogger(RegisterProducerImpl.class);

    @Autowired
    private KafkaProducerFactory kafkaProducerFactory;


    @Override
    public void produceRegister(List<LtfGame> registers) {
        LOG.info("producing " + registers.size() + " registers.");

        final KafkaProducer<String, String> producer = kafkaProducerFactory.createProducer();
        final ProducerCallBack callback = new ProducerCallBack();

        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        registers.forEach(r -> {
            LOG.debug(gson.toJson(r));
            final ProducerRecord<String, String> producerRecord = kafkaProducerFactory.createProducerRecord(gson.toJson(r));
            producer.send(producerRecord , callback);
        });

        producer.flush();
        producer.close();
    }
}
