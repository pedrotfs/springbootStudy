package br.com.pedrotfs.crawler.executor;

import br.com.pedrotfs.crawler.assembler.Assembler;
import br.com.pedrotfs.crawler.file.Decompresser;
import br.com.pedrotfs.crawler.file.Downloader;
import br.com.pedrotfs.crawler.file.Parser;
import br.com.pedrotfs.crawler.kafka.RegisterProducer;
import br.com.pedrotfs.crawler.kafka.RequestConsumer;
import br.com.pedrotfs.crawler.mongo.MongoClientWrapper;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Autowired
    private MongoClientWrapper mongoWrapper;

    MongoDatabase database;

    public void start() throws InterruptedException {
        LOG.info("Starting crawler execution.");
        database = mongoWrapper.setUpDbConnection();

        String pooledRequest = mongoWrapper.recoverLastRequest();
        String previousRequest = pooledRequest;
        while(!pooledRequest.equalsIgnoreCase("X"))
        {
            if(pooledRequest.equalsIgnoreCase(previousRequest)) {
                LOG.info("No requests pooled. standing idle.");
            } else {
                mongoWrapper.persistRequest(pooledRequest);
                executeFlow();
            }
            previousRequest = pooledRequest;
            pooledRequest = requestConsumer.consumeFeed();
            Thread.sleep(10000);
        }
        LOG.info("Ending crawler execution.");
    }

    private void executeFlow() {
        if(shouldUpdateSource()) { //TODO here to test schedules
            LOG.info("Updating source was deemed necessary.");
            downloader.download(fileLocation, fileName);
            decompresser.decompress(extractTo, fileName, zippedName);
            registerProducer.produceRegister(assembler.assemble(parser.parse(extractTo)));
        }
    }

    private boolean shouldUpdateSource() {
        return mongoWrapper.shouldUpdateSource();
    }

    private boolean shouldDebugSource()
    {
        return false;
    }

    private boolean debug()
    {
        return false;
    }
}
