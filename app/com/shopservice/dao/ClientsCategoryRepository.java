package com.shopservice.dao;

import com.shopservice.domain.ClientsCategory;

import java.util.Collection;
import java.util.List;

public interface ClientsCategoryRepository {
    List<ClientsCategory> get(String clientId);
    ClientsCategory add(String clientId, ClientsCategory clientsCategory);
    ClientsCategory update(String clientId, ClientsCategory clientsCategory);
    void delete(int clientsCategoryId);
    List<ClientsCategory> getByParent(Integer parentId);

    List<ClientsCategory>  getParents(Collection<Integer> categoryIds);
}
