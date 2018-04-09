/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Michele Perissinotto
 */
@ProviderType
public class TaskImpl extends TaskBaseImpl {

	@Override
	public String getTaskUserFullName() throws PortalException {
		try {
			User user = UserLocalServiceUtil.getUser(getTaskUserId());

			return user.getFullName();
		}
		catch (NoSuchUserException nsue) {
			return StringPool.BLANK;
		}
	}

}