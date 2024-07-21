package com.supermart.core.daos.impl;

import com.supermart.core.daos.SupermartCustomerDao;
import de.hybris.platform.commerceservices.customer.dao.impl.DefaultCustomerDao;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;
import java.util.Map;

public class SupermartCustomerDaoImpl extends DefaultCustomerDao implements SupermartCustomerDao
{
    private static final String FIND_ALL_CUSTOMER = "SELECT {pk} FROM {Customer}";

    @Override
    public List<CustomerModel> findAllCustomers()
    {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_ALL_CUSTOMER);
        final SearchResult<CustomerModel> result = getFlexibleSearchService().search(query);
        return result.getResult();
    }

    Map<String, List<String>> getCustomerOrdersProducts(String customerId)
    {
        // customerId=1234
        //	     customer has 3 orders
        //		 Each order has some Products
        //		 map of orderCode, list of products
        return null;
    }
}
