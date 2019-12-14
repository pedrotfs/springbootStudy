package br.com.pedrotfs.crawler.kafka;

import br.com.pedrotfs.crawler.domain.LtfGame;
import br.com.pedrotfs.crawler.domain.MgsGame;

import java.util.List;

public interface RegisterProducer {
    void produceRegister(List<LtfGame> registers);

    void produceRegisterMgs(List<MgsGame> registers);
}
