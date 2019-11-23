package br.com.pedrotfs.crawler.executor;

import br.com.pedrotfs.crawler.domain.LtfGame;
import br.com.pedrotfs.crawler.file.Decompresser;
import br.com.pedrotfs.crawler.file.Downloader;
import br.com.pedrotfs.crawler.file.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    public void start()
    {
        LOG.info("Starting crawler execution.");
        if(shouldUpdateSource()) {
            LOG.info("Updating source was deemed necessary.");
            downloader.download(fileLocation, fileName);
            decompresser.decompress(extractTo, fileName, zippedName);
        }
        parser.parse(extractTo);
        LOG.info("Ending crawler execution.");
    }

    private boolean shouldUpdateSource()
    {
        return false; //needs database to save when last download and schedule
    }
}
