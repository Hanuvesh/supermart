package com.supermart.core.daos;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.daos.OrderDao;
import java.util.List;

public interface SupermartOrderDao extends OrderDao
{
    List<OrderModel> findAllOrders();
}
