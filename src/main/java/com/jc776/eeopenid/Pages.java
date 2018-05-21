package com.jc776.eeopenid;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;
import org.apache.deltaspike.security.api.authorization.Secured;

@View(navigation = NavigationMode.REDIRECT)
public interface Pages extends ViewConfig
{
    class Login implements Pages { }
    class LoginError implements Pages { }
    
    @Secured(value = CustomAccessDecisionVoter.class, errorView = Pages.Login.class)
    interface Admin extends Pages
    {
    	class Secure implements Pages { }
    }
}
