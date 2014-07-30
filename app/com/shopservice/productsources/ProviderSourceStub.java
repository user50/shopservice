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
        products.add(new Product( "Диван угловой Хилтон (Fola)",400, true ));
        products.add(new Product( "Диван угловой Марк (Fola)",1300, true ));
        products.add(new Product( "Диван угловой Лорд-2 (Fola)",1520, false ));
        products.add(new Product( "Диван Миста (Fola)",1200, true ));
        products.add(new Product( "Диван Форум (Fola)",1100, true ));
        products.add(new Product( "Шкаф-купе на заказ. Проект №9.",1400, false ));
        products.add(new Product( "Стелаж Ольвия (O-P-03)",900, true ));
        products.add(new Product( "Туалетный столик с зеркалом Ольвия [O-TR-01]",440, true ));
        products.add(new Product( "Стенка в детскую комнату Наутилус-33",432, true ));
        products.add(new Product( "Софти 80x200",434, true ));
        products.add(new Product( "Стенка в детскую комнату Принцесса 1",1255, true ));
        products.add(new Product( "Кровать Венеция щит бука 90х200 (Estella)",999, true ));
    }

    @Override
    public List<Product> get(Integer providerId) {
        return products;
    }
}
