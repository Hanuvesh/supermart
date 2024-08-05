package com.supermart.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;

public class SupermartRegisterForm extends RegisterForm
{
    private String aadharNumber;
    private String mobileNumber;

    public String getAadharNumber()
    {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber)
    {
        this.aadharNumber = aadharNumber;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
}
