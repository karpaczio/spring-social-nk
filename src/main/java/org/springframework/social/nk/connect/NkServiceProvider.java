package org.springframework.social.nk.connect;

import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.impl.NkTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class NkServiceProvider extends AbstractOAuth2ServiceProvider<Nk> {

	public NkServiceProvider(String clientKey, String clientSecret) {
		super(new OAuth2Template(clientKey, clientSecret,
				"http://3pp.omega.nknet/oauth/authorize",
				//"http://3pp.omega.nknet/oauth/authenticate",
				"http://3pp.omega.nknet/oauth/access_token"));
	}

	public Nk getApi(String accessToken) {
		return new NkTemplate(accessToken);
	}

}
