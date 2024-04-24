package com.example.html;

public class Hockey {
    private String name;
    private int year;
    private int wins;
    private int losses;
    public Hockey(String name , int year , int wins , int losses){
        this.name = name;
        this.losses = losses;
        this.wins = wins;
        this.year = year;
    }

    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }
}
