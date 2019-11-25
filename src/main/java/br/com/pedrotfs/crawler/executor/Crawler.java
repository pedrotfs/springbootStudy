package br.com.pedrotfs.crawler.executor;

import br.com.pedrotfs.crawler.assembler.Assembler;
import br.com.pedrotfs.crawler.domain.LtfGame;
import br.com.pedrotfs.crawler.file.Decompresser;
import br.com.pedrotfs.crawler.file.Downloader;
import br.com.pedrotfs.crawler.file.Parser;
import br.com.pedrotfs.crawler.kafka.RegisterProducer;
import br.com.pedrotfs.crawler.kafka.RequestConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Configurable
public class Crawler {

    @Value("${ltf.file.source.location}")
    private String fileLocation;

    @Value("${ltf.file.target.name}")
    private String fileName;

    @Value("${ltf.file.unzip.name}")
    private String extractTo;

    @Value("${ltf.file.zipped}")
    private String zippedName;

    private static Logger LOG = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private Downloader downloader;

    @Autowired
    private Decompresser decompresser;

    @Autowired
    private Parser parser;

    @Autowired
    private Assembler assembler;

    @Autowired
    private RegisterProducer registerProducer;

    @Autowired
    private RequestConsumer requestConsumer;

    public void start() throws InterruptedException {
        LOG.info("Starting crawler execution.");

        String pooledRequest = "";
        while(!pooledRequest.equalsIgnoreCase("X"))
        {
            if(pooledRequest.isEmpty()) {
                LOG.info("No requests pooled. standing idle.");
                Thread.sleep(10000);
            } else {
                executeFlow();
            }
            pooledRequest = requestConsumer.consumeFeed();
        }
        LOG.info("Ending crawler execution.");
    }

    private void executeFlow() {
        if(shouldUpdateSource()) {
            LOG.info("Updating source was deemed necessary.");
            downloader.download(fileLocation, fileName);
            decompresser.decompress(extractTo, fileName, zippedName);
        }
        registerProducer.produceRegister(assembler.assemble(parser.parse(extractTo)));
    }

    private boolean shouldUpdateSource()
    {
        return false; //needs database to save when last download and schedule
    }
}
