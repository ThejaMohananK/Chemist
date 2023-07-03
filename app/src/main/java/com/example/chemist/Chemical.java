package com.example.chemist;

public class Chemical {
    private String name;
    private String symbol;

    public Chemical() {}

    public Chemical(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;

    }

    // Getters and setters for the properties
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {

        return name + ":" + symbol;
    }
}
