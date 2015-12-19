package com.shopservice.datasources;

public enum Urls {
    local("jdbc:mysql://localhost:3306/shopservice?useUnicode=yes&characterEncoding=utf8" +
            "&user=root&password=neuser50"),

    bilasad("jdbc:mysql://148.251.95.195:3306/bilasadc_mservice?useUnicode=yes&characterEncoding=utf8" +
            "&user=bilasadc_service&password=suAOwtw7Ow"),

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
