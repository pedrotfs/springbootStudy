package br.com.pedrotfs.crawler.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame {

    private int _id;

    private List<Integer> numbers = new ArrayList<>();

    private int numberCount;

    private int numberLimit;

    private int winnerCategories;

    private List<Long> winnerCategoriesDividends = new ArrayList<>();

    private List<Integer> winnerCategoriesAmount = new ArrayList<>();

    private Long value;

    private LocalDate date;

    private String name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
