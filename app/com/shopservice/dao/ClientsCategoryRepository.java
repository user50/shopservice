package com.shopservice.dao;

import com.shopservice.domain.ClientsCategory;

import java.util.List;

public interface ClientsCategoryRepository {
    List<ClientsCategory> get(String clientId);
    ClientsCategory add(String clientId, ClientsCategory clientsCategory);
    ClientsCategory update(String clientId, ClientsCategory clientsCategory);
    void delete(int clientsCategoryId);
}
