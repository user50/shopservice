package com.shopservice;

import com.shopservice.dao.LinkedProductEntryRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.dao.ProductProviderRepository;
import com.shopservice.domain.LinkedProductEntry;
import com.shopservice.transfer.Product;
import com.shopservice.domain.ProductEntry;
import com.shopservice.productsources.ProductSource;
import com.shopservice.productsources.ProviderSourceStub;

import java.util.*;

public class ActualisationService {

    private LinkedProductEntryRepository linkedEntryRepository = MServiceInjector.injector.getInstance(LinkedProductEntryRepository.class);
    private ProductProviderRepository productProviderRepository = MServiceInjector.injector.getInstance(ProductProviderRepository.class);
    private ProductEntryRepository productEntryRepository = MServiceInjector.injector.getInstance(ProductEntryRepository.class);

    public List<Product> getNotLinkedProducts(String clientId, Integer providerId, String words)
    {
        List<LinkedProductEntry> entries = linkedEntryRepository.find(new LinkedEntryCondition(providerId));
        Set<String> linkedNames = new HashSet<String>();
        for (LinkedProductEntry entry : entries)
            linkedNames.add(entry.name);

        List<Product> notLinkedProducts = new ArrayList<>();
        ProductSource source = new ProviderSourceStub();
        for (Product product : source.get(providerId))
            if (!linkedNames.contains(product.name) && contain(product.name, words))
                notLinkedProducts.add(product);

        return notLinkedProducts;

    }

    private static boolean contain(String name, String words) {
        if (words==null)
            return true;

        String[] splited = words.split(" ");

        for (String likeWord : splited) {
            if (!Util.matches(".*"+likeWord+".*", name))
                return false;
        }

        return true;
    }

    public void autoLink(String clientId, Integer providerId) {
        for (Product product : getNotLinkedProducts(clientId, providerId, null)) {
            ProductConditions conditions = new ProductConditions();
            conditions.words = Arrays.asList( product.name.split(" ") );

            List<Product> sameNamedProducts = Services.getProductDAO(clientId).find(conditions);

            if (sameNamedProducts.size() == 1)
            {
                Product sameNamedProduct = sameNamedProducts.get(0);

                ProductEntry productEntry = productEntryRepository.find(clientId, sameNamedProduct.id);

                if (productEntry == null)
                    continue;

                LinkedProductEntry entry = new LinkedProductEntry();
                entry.name = product.name;
                entry.productProvider = productProviderRepository.find(providerId);
                entry.productEntry = productEntry;

                linkedEntryRepository.create(entry);
            }
        }
    }
}
