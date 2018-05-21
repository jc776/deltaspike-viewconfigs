package com.jc776.eeopenid;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;

import com.nimbusds.oauth2.sdk.ParseException;

@RequestScoped
public class AuthProvidersCurrentSite {
	private static AuthProviderModel provider;

	public AuthProviderModel getProvider() {
		if (provider == null) {
			try {
				provider = AuthProviderModel.create(1L, "Keycloak", true,
						"https://auth.ocgsoftware.com/auth/realms/sps", "local",
						"f585ad9d-6dfa-4bfb-9f58-961dc98b298f");
			} catch (ParseException | IOException e) {
				throw new RuntimeException(e);
			}
		}
		return provider;
	}
}
