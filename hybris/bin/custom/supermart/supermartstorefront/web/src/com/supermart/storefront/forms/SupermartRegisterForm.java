package com.supermart.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;

public class SupermartRegisterForm extends RegisterForm
{

    private String aadharNumber;

    public String getAadharNumber()
    {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber)
    {
        this.aadharNumber = aadharNumber;
    }

}
