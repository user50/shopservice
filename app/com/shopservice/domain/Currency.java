package com.shopservice.domain;

public enum Currency {
    UAH("1.0"), USD("8.0");

    private String rate;

    private Currency(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }
}
