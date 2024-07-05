/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.supermart.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.supermart.fulfilmentprocess.constants.SupermartFulfilmentProcessConstants;

public class SupermartFulfilmentProcessManager extends GeneratedSupermartFulfilmentProcessManager
{
	public static final SupermartFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SupermartFulfilmentProcessManager) em.getExtension(SupermartFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
