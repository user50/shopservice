package com.shopservice.productsources;

import com.shopservice.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProviderSourceStub implements ProductSource {

    private List<Product> products = new ArrayList<>();

    public ProviderSourceStub() {
        products.add(new Product( "Парта детская СУ 04",850, true ));
        products.add(new Product( "Парта детская Макс-мини",1030, true ));
        products.add(new Product( "Парта детская Макс-макси",1130, true ));
        products.add(new Product( "Парта детская Такси [Эдисан] PR-01",1490, true ));
        products.add(new Product( "Парта детская Полиция [Эдисан] PR-01",1490, true ));
        products.add(new Product( "Парта детская Спорт (Эдисан) PR-01",1490, true ));
        products.add(new Product( "Ученическая парта Макс-AS",1600, true ));
        products.add(new Product( "Стол компьютерный Winx С-27",1400, true ));
    }

    @Override
    public List<Product> get(Integer providerId) {
        return products;
    }
}
