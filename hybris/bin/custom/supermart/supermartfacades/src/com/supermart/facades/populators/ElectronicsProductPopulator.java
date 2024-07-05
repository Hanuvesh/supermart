/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.supermart.facades.populators;

import com.supermart.core.model.ApparelProductModel;
import com.supermart.core.model.ElectronicsColorVariantProductModel;
import com.supermart.facades.product.data.GenderData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.Gender;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;


/**
 * Populates {@link ProductData} with genders
 */
public class ElectronicsProductPopulator implements Populator<ProductModel, ProductData>
{
	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{
		if (source instanceof ElectronicsColorVariantProductModel) {
			final ElectronicsColorVariantProductModel variant = (ElectronicsColorVariantProductModel) source;
			if (variant.getLaunchDate() != null) {
				target.setLaunchDate(variant.getLaunchDate().toString());
			}
		}
	}
}
