package org.springframework.social.nk.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.nk.api.Nk;

public class NkConnectionFactory extends OAuth2ConnectionFactory<Nk> {

	public NkConnectionFactory(String clientKey, String clientSecret) {

		super("nk", new NkServiceProvider(clientKey, clientSecret),
				new NkApiAdapter());
	}
}
