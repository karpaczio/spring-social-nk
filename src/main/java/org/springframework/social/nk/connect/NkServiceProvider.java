package org.springframework.social.nk.connect;

import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.impl.NkTemplate;
import org.springframework.social.nk.oauth2.NkOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class NkServiceProvider extends AbstractOAuth2ServiceProvider<Nk> {

	private final ProtectedResourceDetails resource;

	public NkServiceProvider(String clientKey, String clientSecret,
			ProtectedResourceDetails resource) {
		// TODO change to OAuth2Template when bug https://mgt.nkdev.pl/jira/browse/NK-12707 will be fixed
		super(new NkOAuth2Template(clientKey, clientSecret,
				"https://ssl.3pp.omega.nknet/oauth2/login",
				// "http://ssl.3pp.omega.nknet/oauth/authenticate",
				"http://3pp.omega.nknet/oauth2/token"));
		this.resource = resource;

	}

	public Nk getApi(String accessToken) {
		return new NkTemplate(accessToken, resource);
	}

}
