package com.supermart.core.job;


import com.supermart.core.daos.SupermartProductDao;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.enums.CronJobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ProductListJobPerformable extends AbstractJobPerformable<CronJobModel>
{
    private static final Logger LOG = LoggerFactory.getLogger(ProductListJobPerformable.class);

    private SupermartProductDao supermartProductDao;

    @Override
    public PerformResult perform(final CronJobModel cronJob)
    {
    final List<ProductModel> products =  supermartProductDao.findProductsByFirstLetter("1");
        LOG.info("******Printing products starting with i********");
        products.stream().forEach(this::printProductCodes);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    protected void printProductCodes(final ProductModel productModel)
    {
        LOG.info(productModel.getCode()+" "+ productModel.getName());
    }

    public SupermartProductDao getSupermartProductDao()
    {
        return supermartProductDao;
    }

    public void setSupermartProductDao(SupermartProductDao supermartProductDao)
    {
        this.supermartProductDao = supermartProductDao;
    }
}
