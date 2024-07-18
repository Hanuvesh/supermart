package com.supermart.core.interceptors;

import com.supermart.core.model.ElectronicsColorVariantProductModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SupermartProductValidationInterceptor implements ValidateInterceptor<ElectronicsColorVariantProductModel>
{
    @Override
    public void onValidate(ElectronicsColorVariantProductModel electronicsColorVariantProductModel, InterceptorContext interceptorContext) throws InterceptorException
    {
      Date launchDate = electronicsColorVariantProductModel.getLaunchDate();
      if(launchDate!= null)
      {
          LocalDate oneYearAgo = LocalDate.now().minusYears(1);
          LocalDate launchLocalDate = launchDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                  if(launchLocalDate.isBefore(oneYearAgo)){
                      throw new InterceptorException("The launch date cannot be more than 1 year old");
                  }
      }
    }
}
