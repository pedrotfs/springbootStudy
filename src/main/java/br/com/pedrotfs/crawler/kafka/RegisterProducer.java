package br.com.pedrotfs.crawler.kafka;

import br.com.pedrotfs.crawler.domain.LtfGame;

import java.util.List;

public interface RegisterProducer {
    void produceRegister(List<LtfGame> registers);
}
