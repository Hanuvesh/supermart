package com.supermart.core.daos.impl;

import com.supermart.core.daos.SupermartOrderDao;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.daos.impl.DefaultOrderDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

public class SupermartOrderDaoImpl extends DefaultOrderDao implements SupermartOrderDao
{
    private static final String FIND_ALL_ORDERS = "SELECT {pk} FROM {Order}";

    @Override
    public List<OrderModel> findAllOrders()
    {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_ALL_ORDERS);
        final SearchResult<OrderModel> result = getFlexibleSearchService().search(query);
        return result.getResult();
    }
}
