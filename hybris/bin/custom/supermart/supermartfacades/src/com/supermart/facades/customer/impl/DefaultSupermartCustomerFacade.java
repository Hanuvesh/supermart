package com.supermart.facades.customer.impl;

import com.supermart.facades.customer.SupermartCustomerFacade;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.core.model.user.CustomerModel;

public class DefaultSupermartCustomerFacade extends DefaultCustomerFacade implements SupermartCustomerFacade
{
    @Override
    protected void setCommonPropertiesForRegister(final RegisterData registerData, final CustomerModel customerModel)
    {
        customerModel.setAadharNumber(registerData.getAadharNumber());
        super.setCommonPropertiesForRegister(registerData, customerModel);
    }
}
