/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Michele Perissinotto
 */
@ProviderType
public class NoSuchTaskException extends NoSuchModelException {

	public NoSuchTaskException() {
	}

	public NoSuchTaskException(String msg) {
		super(msg);
	}

	public NoSuchTaskException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchTaskException(Throwable cause) {
		super(cause);
	}

}