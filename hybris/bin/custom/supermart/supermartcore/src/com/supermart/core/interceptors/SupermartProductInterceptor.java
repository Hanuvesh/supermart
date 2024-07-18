package com.supermart.core.interceptors;

import com.supermart.core.model.ElectronicsColorVariantProductModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import java.util.Date;

public class SupermartProductInterceptor implements PrepareInterceptor<ElectronicsColorVariantProductModel>
{
    @Override
    public void onPrepare(ElectronicsColorVariantProductModel electronicsColorVariantProductModel, InterceptorContext interceptorContext) throws InterceptorException
    {
      //  electronicsColorVariantProductModel.setLaunchDate(new Date());
    }
}
