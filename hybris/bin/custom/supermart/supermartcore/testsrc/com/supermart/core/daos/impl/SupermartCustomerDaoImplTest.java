package com.supermart.core.daos.impl;

import com.google.common.collect.Lists;
import com.supermart.core.daos.SupermartCustomerDao;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SupermartCustomerDaoImplTest extends TestCase
{
    SupermartCustomerDao supermartCustomerDao = new SupermartCustomerDaoImpl();
    public void setUp() throws Exception
    {
        super.setUp();
    }

    public void tearDown() throws Exception
    {
    }

    @Test
    public void test1()
    {
        System.out.println("This is a test");
    }

    @Test
    public void test2()
    {
        Map<String, List<String>> result =  supermartCustomerDao.getCustomerOrdersProducts("100");
        System.out.println(result);
    }

    private List<CustomerModel> getCustomers()
    {
        final List<CustomerModel> customers = new ArrayList<>();
        final CustomerModel customer1 = new CustomerModel();
        customer1.setCustomerID("123");

        final CustomerModel customer2 = new CustomerModel();
        customer2.setCustomerID("100");

        final OrderModel order1 = new OrderModel();
        order1.setCode("1000");
        final OrderEntryModel entry1 = new OrderEntryModel();
        final ProductModel product1 = new ProductModel();
        product1.setCode("10");
        entry1.setProduct(product1);

        final OrderModel order2 = new OrderModel();
        order2.setCode("2000");
        final OrderEntryModel entry2 = new OrderEntryModel();
        final ProductModel product2 = new ProductModel();
        product2.setCode("20");
        entry2.setProduct(product2);

        order1.setEntries(Lists.newArrayList(entry1,entry2));
        order2.setEntries(Lists.newArrayList(entry2));

        customer1.setOrders(Lists.newArrayList(order1,order2));
        customer2.setOrders(Lists.newArrayList(order2));

        customers.add(customer1);
        customers.add(customer2);

        return customers;
    }
}


