package com.shopservice.datasources;

public enum Urls {
    local("jdbc:mysql://localhost:3306/shopservice?useUnicode=yes&characterEncoding=utf8" +
            "&user=root&password=neuser50"),

    heroku("jdbc:mysql://us-cdbr-east-05.cleardb.net:3306/heroku_20e5b087480e48d?useUnicode=yes&characterEncoding=utf8" +
            "&user=b02276676df1a5&password=2c270044");

    private String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
