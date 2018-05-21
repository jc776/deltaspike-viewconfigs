package com.jc776.eeopenid;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

public class SsoAuthenticator {
	
	@Inject
	private transient Logger logger;

	public Class<? extends ViewConfig> login() {
		logger.info("err");
		return Pages.LoginError.class;
	}

}
