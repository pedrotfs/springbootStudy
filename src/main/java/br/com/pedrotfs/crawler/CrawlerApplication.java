package br.com.pedrotfs.crawler;

import br.com.pedrotfs.crawler.executor.Crawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CrawlerApplication {

    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext context = SpringApplication.run(CrawlerApplication.class, args);
        context.getBeanFactory().getBean("crawler", Crawler.class).start();
	}
}
