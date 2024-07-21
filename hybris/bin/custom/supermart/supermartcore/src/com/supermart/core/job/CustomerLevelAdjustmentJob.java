package com.supermart.core.job;

import com.supermart.core.daos.SupermartCustomerDao;
import com.supermart.core.enums.CustomerLevel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class CustomerLevelAdjustmentJob extends AbstractJobPerformable<CronJobModel>
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerLevelAdjustmentJob.class);

    private SupermartCustomerDao supermartCustomerDao;

    @Override
    public PerformResult perform(CronJobModel cronJobModel)
    {
        // to update the customer level based on his orders value
        // Rules-> silver (more than 100), gold(more than 500), platinum(more than 1000)
        // get list of all customers
        // iterate over the list
        // in each iteration check the total value of orders placed by Customer
        // set the customer level based on rules

        List<CustomerModel> customers = getSupermartCustomerDao().findAllCustomers();
        customers.forEach(customerModel -> {
            final double orderTotal = getOrderTotalForCustomer(customerModel);
            CustomerLevel customerLevel = null;
            if(orderTotal>1000){
                customerLevel = CustomerLevel.PLATINUM;
            } else if (orderTotal>500)
            {
                customerLevel = CustomerLevel.GOLD;
            } else if (orderTotal>100)
            {
                customerLevel = CustomerLevel.SILVER;
            }
            if (customerLevel != null && !customerLevel.equals(customerModel.getLevel())){
                customerModel.setLevel(customerLevel);
                modelService.save(customerModel);
            }
        });
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private double getOrderTotalForCustomer(CustomerModel customerModel)
    {
        return customerModel.getOrders().stream()
                .map(order -> order.getTotalPrice())
                .reduce(Double::sum)
                .orElse(0.0);
    }

    public SupermartCustomerDao getSupermartCustomerDao()
    {
        return supermartCustomerDao;
    }

    @Required
    public void setSupermartCustomerDao(SupermartCustomerDao supermartCustomerDao)
    {
        this.supermartCustomerDao = supermartCustomerDao;
    }

}


