package com.jc776.eeopenid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.Prompt;

public class SsoAuthenticator {

	@Inject
	private transient Logger logger;
	@Inject
	private SsoSession session;

	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public String getAuthLink(final AuthProviderModel provider, final boolean quiet, final boolean auto) {
		try {
			final State state = new State();
			final Nonce nonce = new Nonce();
			final String claims = provider.isCompany() ? "openid email company" : //$NON-NLS-1$
					"openid email"; //$NON-NLS-1$
			final Scope scope = Scope.parse(claims);
			final Prompt prompt = quiet ? Prompt.parse("none") : null;

			final ClientID clientId = provider.getClientId();
			final URI endpointUri = provider.getOidcMeta().getAuthorizationEndpointURI();
			final URI redirectUri = getCallbackUri(getExternalContext(), provider);
			final ResponseType responseType = new ResponseType("code");
			final Map<String, String> params = new HashMap<>();
			if (auto) {
				params.put("kc_idp_hint", "okta");
			}
			final AuthenticationRequest req = new AuthenticationRequest(endpointUri, responseType, null, scope,
					clientId, redirectUri, state, nonce, null, prompt, -1, null, null, null, null, null, null, null,
					null, null, null, params);
			return req.toURI().toString();
		} catch (URISyntaxException | ParseException e) {
			logger.info("ERR - " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public URI getCallbackUri(final ExternalContext ctx, final AuthProviderModel provider)
			throws URISyntaxException {
		logger.info(ctx.getRequestServerName());
		return new URI("http", "localhost:8080", "/eeopenid/pages/login.xhtml", null, null); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Inject
	private AuthProvidersCurrentSite authProvidersCurrentSite;

	public Class<? extends ViewConfig> login() {
		try {
			final AuthProviderModel provider = authProvidersCurrentSite.getProvider();
			final ExternalContext ctx = getExternalContext();
			
			final Map<String, String[]> params = ctx.getRequestParameterValuesMap();
			
			final String[] errs = params.get("error");
			if(errs != null) {
				logger.info(errs.toString());
			}
			
			final String[] code = params.get("code");
			if(code != null) {
				session.setSubject(code[0]);
				return Pages.Admin.Secure.class;
			}
			
			if (session.getTried()) {
				logger.info("redirect to login");
				ctx.redirect(getAuthLink(provider, false, false));
			} else {
				logger.info("try auto login");
				session.setTried(true);
				final String link = getAuthLink(provider, true, true) ;
						logger.info(link);
				ctx.redirect(link);
			}
			return null;

		} catch (IOException ex) {
			logger.info("ERR2 - " + ex.getMessage());
		}
		return Pages.LoginError.class;
	}

}
