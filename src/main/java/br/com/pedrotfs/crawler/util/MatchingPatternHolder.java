package br.com.pedrotfs.crawler.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchingPatternHolder {

    //Matching fields
    @Value("${ltf.pattern.prefix.even}")
    private String ltfEven;

    @Value("${ltf.pattern.prefix.odd}")
    private String ltfOdd;

    //util
    @Value("${pattern.split}")
    private String split;

    private List<Character> patterns;

    public String getLtfEven() {
        return ltfEven;
    }

    public String getLtfOdd() {
        return ltfOdd;
    }

    public String getSplit() {
        return split;
    }

    public List<Character> getInvalidChars()
    {
        if(patterns == null) {
            patterns = new ArrayList<>();
            patterns.add('\n');
            patterns.add('=');
            patterns.add(' ');
            patterns.add('#');
            patterns.add('&');
            patterns.add(';');
            patterns.add('�');
        }
        return patterns;
    }
}
