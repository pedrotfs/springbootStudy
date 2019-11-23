package br.com.pedrotfs.crawler.file;

import java.util.List;

public interface Parser {

    List<String> parse(final String originFileName);
}
