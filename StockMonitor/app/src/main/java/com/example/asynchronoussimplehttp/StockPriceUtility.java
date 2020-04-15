package com.example.asynchronoussimplehttp;

import java.util.ArrayList;

public class StockPriceUtility {

    private String indentifier;
    private double price;

    public StockPriceUtility(String identifier, double price){
        this.indentifier = identifier;
        this.price = price;
    }

    public String getIndentifier() {
        return indentifier;
    }

    public double getPrice() {
        return price;
    }
}
