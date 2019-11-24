package br.com.pedrotfs.crawler.assembler;

import br.com.pedrotfs.crawler.domain.LtfGame;

import java.util.List;

public interface Assembler {
    List<LtfGame> assemble(List<String> list);
}
