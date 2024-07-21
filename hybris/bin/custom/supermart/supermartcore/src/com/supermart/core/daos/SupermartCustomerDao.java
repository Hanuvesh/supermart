package com.supermart.core.daos;

import de.hybris.platform.commerceservices.customer.dao.CustomerDao;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface SupermartCustomerDao extends CustomerDao
{
    List<CustomerModel> findAllCustomers();
}
