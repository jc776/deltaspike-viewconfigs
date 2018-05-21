package com.jc776.eeopenid;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderConfigurationRequest;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.io.IOException;

public final class AuthProviderModel {
	private final Long id;
	private final String name;
	private final boolean company;
	private final ClientID clientId;
	private final Secret clientSecret;
	private final OIDCProviderMetadata oidcMeta;

	public static AuthProviderModel create(final Long id, final String name, final boolean company, final String url,
			final String clientId, final String clientSecret) throws IOException, ParseException {

		final Issuer issuer = new Issuer(url);
		final OIDCProviderConfigurationRequest request = new OIDCProviderConfigurationRequest(issuer);
		final HTTPRequest httpRequest = request.toHTTPRequest();
		final HTTPResponse httpResponse = httpRequest.send();
		final OIDCProviderMetadata meta = OIDCProviderMetadata.parse(httpResponse.getContentAsJSONObject());
		return new AuthProviderModel(id, name, company, meta, new ClientID(clientId), new Secret(clientSecret));
	}

	private AuthProviderModel(final Long id, final String name, final boolean company, final OIDCProviderMetadata meta,
			final ClientID clientId, final Secret clientSecret) {
		this.id = id;
		this.name = name;

		this.company = company;
		this.oidcMeta = meta;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public ClientID getClientId() {
		return this.clientId;
	}

	public Secret getClientSecret() {
		return this.clientSecret;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public OIDCProviderMetadata getOidcMeta() {
		return this.oidcMeta;
	}

	public boolean isCompany() {
		return this.company;
	}
}