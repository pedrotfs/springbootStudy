package br.com.pedrotfs.crawler.assembler.impl;

import br.com.pedrotfs.crawler.assembler.Assembler;
import br.com.pedrotfs.crawler.domain.LtfGame;
import br.com.pedrotfs.crawler.util.MatchingPatternHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class AssemblerImpl implements Assembler {

    @Autowired
    private MatchingPatternHolder matchingPatternHolder;

    private static Logger LOG = LoggerFactory.getLogger(AssemblerImpl.class);

    @Override
    public List<LtfGame> assemble(List<String> list) {
        LOG.info("Assembling parsed objects");
        List<LtfGame> results = new ArrayList<>();
        list.forEach(l -> convertLine(results, l));
        LOG.info("assembly finished succesfully. " + results.size() + " items inbound for transfer.");
        return results;
    }

    private void convertLine(List<LtfGame> result, String line) {
        final String[] split = line.split(matchingPatternHolder.getSplit());
        LtfGame register = new LtfGame();

        register.setId(Integer.parseInt(split[1]));
        populateNumbers(split, register);
        populateCategoriesAmount(split, register);
        populateCategoriesDividends(split, register);

        Collections.sort(register.getNumbers());
        Collections.sort(register.getWinnerCategoriesDividends());
        Collections.sort(register.getWinnerCategoriesAmount());
        Collections.reverse(register.getWinnerCategoriesDividends());
        register.setValue(register.getWinnerCategoriesDividends().get(0) * register.getWinnerCategoriesAmount().get(0));

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.ENGLISH);
        register.setDate(LocalDate.parse(split[2], formatter));

        result.add(register);
    }

    private void populateCategoriesDividends(String[] split, LtfGame register) {
        register.getWinnerCategoriesDividends().add(Long.parseLong(split[24].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesDividends().add(Long.parseLong(split[25].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesDividends().add(Long.parseLong(split[26].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesDividends().add(Long.parseLong(split[27].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesDividends().add(Long.parseLong(split[28].replace(".", "").replace(",", "")));
    }

    private void populateCategoriesAmount(String[] split, LtfGame register) {
        register.getWinnerCategoriesAmount().add(Integer.parseInt(split[19].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesAmount().add(Integer.parseInt(split[20].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesAmount().add(Integer.parseInt(split[21].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesAmount().add(Integer.parseInt(split[22].replace(".", "").replace(",", "")));
        register.getWinnerCategoriesAmount().add(Integer.parseInt(split[23].replace(".", "").replace(",", "")));
    }

    private void populateNumbers(String[] split, LtfGame register) {
        register.getNumbers().add(Integer.parseInt(split[3]));
        register.getNumbers().add(Integer.parseInt(split[4]));
        register.getNumbers().add(Integer.parseInt(split[5]));
        register.getNumbers().add(Integer.parseInt(split[6]));
        register.getNumbers().add(Integer.parseInt(split[7]));
        register.getNumbers().add(Integer.parseInt(split[8]));
        register.getNumbers().add(Integer.parseInt(split[9]));
        register.getNumbers().add(Integer.parseInt(split[10]));
        register.getNumbers().add(Integer.parseInt(split[11]));
        register.getNumbers().add(Integer.parseInt(split[12]));
        register.getNumbers().add(Integer.parseInt(split[13]));
        register.getNumbers().add(Integer.parseInt(split[14]));
        register.getNumbers().add(Integer.parseInt(split[15]));
        register.getNumbers().add(Integer.parseInt(split[16]));
        register.getNumbers().add(Integer.parseInt(split[17]));
    }
}
