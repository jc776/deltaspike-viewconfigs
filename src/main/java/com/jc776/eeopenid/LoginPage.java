package com.jc776.eeopenid;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;

@ViewRef(config = Pages.Login.class)
public class LoginPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
    private ViewNavigationHandler viewNavigationHandler;
	@Inject
	private SsoAuthenticator ssoAuthenticator;
	
	@PreRenderView
	public void pageAction() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			final Class<? extends ViewConfig> res = ssoAuthenticator.login();
			if(res != null) {
				viewNavigationHandler.navigateTo(res);
			}
		}
	}
}
