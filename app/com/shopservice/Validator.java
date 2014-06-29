package com.shopservice;

import com.shopservice.dao.ProductGroupRepository;
import com.shopservice.domain.ProductGroup;
import com.shopservice.exception.Description;
import com.shopservice.exception.ValidationException;

import static com.shopservice.MServiceInjector.injector;

/**
 * Created by user50 on 29.06.2014.
 */
public class Validator {
    private static ProductGroupRepository productGroupRepository = injector.getInstance(ProductGroupRepository.class);

    public static void validate(ProductGroup group)
    {
        if (group.name != null && group.name.isEmpty())
            throw new ValidationException(new Description("Name cannot be empty", 101));

        if (group.id == null && productGroupRepository.exist(Util.getCurrentClientId(), group.name))
            throw new ValidationException(new Description("Group with specified name already exists", 102));

        if (group.id != null)
        {
            String name = productGroupRepository.get(group.id.longValue()).name;
            if (!name.equals(group.name) && productGroupRepository.exist(Util.getCurrentClientId(), group.name) )
                throw new ValidationException(new Description("Group with specified name already exists", 102));

        }

        if (group.format == null)
            throw new ValidationException(new Description("Format must be specified", 103));

        if (group.format.equals(PriceListType.price) && ( group.productCurrency != null || group.regionalCurrency != null ))
            throw new ValidationException(new Description("Currency cannot be specify for Hotline format", 104 ));

        if (group.format.equals(PriceListType.YML) && group.regionalCurrency == null)
            throw new ValidationException(new Description("Regional Currency must be specify for YML format",105));

        if (group.format.equals(PriceListType.YML) && group.productCurrency != null ^ group.rate != null )
            throw new ValidationException(new Description("XOR",106));

        if (group.rate != null && group.rate.isEmpty())
            throw new ValidationException((new Description("Rate cannot be empty", 107)));
    }

}
