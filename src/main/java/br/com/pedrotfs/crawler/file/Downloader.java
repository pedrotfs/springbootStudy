package br.com.pedrotfs.crawler.file;

public interface Downloader {

    void download(final String fileLocation, final String fileName);
}
