/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.supermart.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.supermart.core.constants.SupermartCoreConstants;
import com.supermart.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class SupermartCoreManager extends GeneratedSupermartCoreManager
{
	public static final SupermartCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SupermartCoreManager) em.getExtension(SupermartCoreConstants.EXTENSIONNAME);
	}
}
