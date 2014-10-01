package com.shopservice.dao;

import com.shopservice.domain.ProductGroup;

import java.util.List;

/**
 * Created by user50 on 29.06.2014.
 */
public abstract class ProductGroupWrapper implements ProductGroupRepository  {

    private ProductGroupRepository repository;

    protected ProductGroupWrapper(ProductGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductGroup> get(String clientId) {
        return repository.get(clientId);
    }

    @Override
    public void save(ProductGroup productGroup) {
        repository.save(productGroup);
    }

    @Override
    public String getName(int id) {
        return repository.getName(id);
    }

    @Override
    public boolean exist(String clientId, String name) {
        return repository.exist(clientId, name);
    }

    @Override
    public void remove(int id) {
        repository.remove(id);
    }

    @Override
    public ProductGroup get(Long groupId) {
        return repository.get(groupId);
    }

    @Override
    public void update(ProductGroup group) {
        repository.update(group);
    }
}
