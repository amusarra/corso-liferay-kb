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
public class TaskTitleException extends PortalException {

	public TaskTitleException() {
	}

	public TaskTitleException(String msg) {
		super(msg);
	}

	public TaskTitleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TaskTitleException(Throwable cause) {
		super(cause);
	}

}