package com.supermart.core.attributehandlers;

import com.supermart.core.daos.SupermartOrderDao;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.stream.Collectors;

public class TotalOrdersForProductHandler implements DynamicAttributeHandler<Integer, ProductModel>
{
    private SupermartOrderDao supermartOrderDao;

    @Override
    public Integer get(ProductModel model)
    {
        /**
         get the list of all the orders
         iterate through orders and increase the count if product is present
          **/
        final List<OrderModel> orders = getSupermartOrderDao().findAllOrders();

        return orders.stream()
                .flatMap(orderModel -> orderModel.getEntries().stream())
                .filter(entry -> entry.getProduct().getCode().equals(model.getCode()))
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public void set(ProductModel model, Integer integer)
    {
           throw new UnsupportedOperationException();
    }

    public SupermartOrderDao getSupermartOrderDao()
    {
        return supermartOrderDao;
    }

    @Required
    public void setSupermartOrderDao(SupermartOrderDao supermartOrderDao)
    {
        this.supermartOrderDao = supermartOrderDao;
    }
}
