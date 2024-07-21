package com.supermart.core.daos;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.daos.ProductDao;

import java.util.List;

public interface SupermartProductDao extends ProductDao
{
    List<ProductModel> findProductsByFirstLetter(String firstLetter);
}
