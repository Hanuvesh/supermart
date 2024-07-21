package com.supermart.core.daos.impl;

import com.supermart.core.daos.SupermartProductDao;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.daos.impl.DefaultProductDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

public class SupermartProductDaoImpl extends DefaultProductDao implements SupermartProductDao
{
    private static final String FIND_PRODUCTS_BY_FIRST_LETTER = "SELECT {pk} FROM {Product} WHERE {code} LIKE ?firstLetter";

    public SupermartProductDaoImpl(String typecode)
    {
        super(typecode);
    }

    @Override
    public List<ProductModel> findProductsByFirstLetter(final String firstLetter)
    {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_PRODUCTS_BY_FIRST_LETTER);
        query.addQueryParameter("firstLetter", firstLetter + "%");
        final SearchResult<ProductModel> result = getFlexibleSearchService().search(query);
        return result.getResult();
    }
}
