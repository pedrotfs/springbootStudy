package br.com.pedrotfs.crawler.domain;

public class LtfGame extends AbstractGame {

    public LtfGame() {
        setNumberCount(15);
        setNumberLimit(25);
    }

    @Override
    public String toString() {
        return "LtfGame{}\n" + super.toString();
    }
}
