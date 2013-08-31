package com.shopservice.domain;

import java.util.ArrayList;
import java.util.List;

public class ClientSettings {
    public String id;
    public String siteName;
    public String siteUrl;
    public String databaseUrl;
    public List<String> productIds = new ArrayList<String>();
}
