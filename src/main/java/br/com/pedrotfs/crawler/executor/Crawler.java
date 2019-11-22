package br.com.pedrotfs.crawler.executor;

import br.com.pedrotfs.crawler.file.Decompresser;
import br.com.pedrotfs.crawler.file.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class Crawler {

    private static Logger LOG = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private Downloader downloader;

    @Autowired
    private Decompresser decompresser;

    public void start()
    {
        LOG.info("Starting crawler execution.");
        if(shouldUpdateSource()) {
            downloader.download();
        }

        LOG.info("Ending crawler execution.");
    }

    private boolean shouldUpdateSource()
    {
        return true;
    }
}
