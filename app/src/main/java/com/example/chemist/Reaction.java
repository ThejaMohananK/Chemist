package com.example.chemist;

public class Reaction {
    private String firstElement;
    private String secondElement;
    private String firstSymbol;
    private String secondSymbol;
    private String reaction;


    public Reaction() {}

    public Reaction(String firstElement, String secondElement, String firstSymbol, String secondSymbol,String reaction) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.firstSymbol = firstSymbol;
        this.secondSymbol = secondSymbol;
        this.reaction = reaction;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(String firstElement) {
        this.firstElement = firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(String secondElement) {
        this.secondElement = secondElement;
    }

    public String getFirstSymbol() {
        return firstSymbol;
    }

    public void setFirstSymbol(String firstSymbol) {
        this.firstSymbol = firstSymbol;
    }

    public String getSecondSymbol() {
        return secondSymbol;
    }

    public void setSecondSymbol(String secondSymbol) {
        this.secondSymbol = secondSymbol;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return firstElement + " ( " + firstSymbol + " ) " + " + " + secondElement + " ( " + secondSymbol + " ) " + " -> " + reaction;
    }
}
