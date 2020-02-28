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
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Value("${mgs.file.source.location}")
    private String fileLocationMgs;

    @Value("${mgs.file.target.name}")
    private String fileNameMgs;

    @Value("${mgs.file.unzip.name}")
    private String extractToMgs;

    @Value("${mgs.file.zipped}")
    private String zippedNameMgs;

    private static Logger LOG = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private Downloader downloader;

    @Autowired
    private Decompresser decompresser;

    @Autowired
    @Qualifier("parserImpl")
    private Parser parser;

    @Autowired
    @Qualifier("parserMgsImpl")
    private Parser parserMgsImpl;

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
            if(pooledRequest.equalsIgnoreCase(previousRequest) || pooledRequest.equalsIgnoreCase("")) {
                LOG.info("No requests pooled. standing idle.");
            } else {
                executeFlow(pooledRequest);
                mongoWrapper.persistRequest(pooledRequest);
            }
            previousRequest = pooledRequest;
            pooledRequest = requestConsumer.consumeFeed();
            Thread.sleep(10000);
        }
        LOG.info("Ending crawler execution.");
    }

    private void executeFlow(final String register) {
        if(register.equalsIgnoreCase("ltf")) {
            executeFlowLtf();
        }
        if(register.equalsIgnoreCase("mgs")) {
            executeFlowMgs();
        }
    }

    private void executeFlowLtf() {
//        if(shouldUpdateSource("ltf")) {
            LOG.info("Updating source was deemed necessary.");
            downloader.download(fileLocation, fileName);
            decompresser.decompress(extractTo, fileName, zippedName);
            registerProducer.produceRegister(assembler.assemble(parser.parse(extractTo)));
//        }
    }

    private void executeFlowMgs() {
//        if(shouldUpdateSource("mgs")) {
            LOG.info("Updating source was deemed necessary [MGS].");
            downloader.download(fileLocationMgs, fileNameMgs);
            decompresser.decompress(extractToMgs, fileNameMgs, zippedNameMgs);
            registerProducer.produceRegisterMgs(assembler.assembleMgs(parserMgsImpl.parse(extractToMgs)));
//        }
    }

    private boolean shouldUpdateSource(final String request) {
        return mongoWrapper.shouldUpdateSource(request);
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
