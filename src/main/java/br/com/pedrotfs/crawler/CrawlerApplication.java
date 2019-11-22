package br.com.pedrotfs.crawler;

import br.com.pedrotfs.crawler.executor.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@SpringBootApplication
@ComponentScan
public class CrawlerApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(CrawlerApplication.class, args);
        context.getBeanFactory().getBean("crawler", Crawler.class).start();
	}
}
