package br.com.pedrotfs.crawler.file;

public interface Decompresser {

    void decompress(final String fileLocation, final String fileName, final String zippedName);
}
