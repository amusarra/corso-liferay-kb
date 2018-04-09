/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michele Perissinotto
 */
@ProviderType
public class ManageTasksException extends PortalException {

	public ManageTasksException() {
	}

	public ManageTasksException(String msg) {
		super(msg);
	}

	public ManageTasksException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ManageTasksException(Throwable cause) {
		super(cause);
	}

}