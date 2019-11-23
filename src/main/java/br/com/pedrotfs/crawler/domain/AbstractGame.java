package br.com.pedrotfs.crawler.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame {

    private int id;

    private List<Integer> numbers = new ArrayList<>();

    private int numberCount;

    private int numberLimit;

    private int winnerCategories;

    private List<Long> winnerCategoriesDividends = new ArrayList<>();

    private List<Integer> winnerCategoriesAmount = new ArrayList<>();

    private Long value;

    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    public int getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(int numberLimit) {
        this.numberLimit = numberLimit;
    }

    public int getWinnerCategories() {
        return winnerCategories;
    }

    public void setWinnerCategories(int winnerCategories) {
        this.winnerCategories = winnerCategories;
    }

    public List<Long> getWinnerCategoriesDividends() {
        return winnerCategoriesDividends;
    }

    public void setWinnerCategoriesDividends(List<Long> winnerCategoriesDividends) {
        this.winnerCategoriesDividends = winnerCategoriesDividends;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public List<Integer> getWinnerCategoriesAmount() {
        return winnerCategoriesAmount;
    }

    public void setWinnerCategoriesAmount(List<Integer> winnerCategoriesAmount) {
        this.winnerCategoriesAmount = winnerCategoriesAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AbstractGame{" +
                "id=" + id +
                ", numbers=" + numbers +
                ", winnerCategories=" + winnerCategories +
                ", value=" + value +
                '}';
    }
}
