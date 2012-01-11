package org.springframework.social.nk.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NkOAuth2Template extends OAuth2Template {

	private final String clientId;

	private final String clientSecret;

	private final String accessTokenUrl;

	public NkOAuth2Template(String clientId, String clientSecret,
			String authorizeUrl, String accessTokenUrl) {
		this(clientId, clientSecret, authorizeUrl, null, accessTokenUrl);
	}

	public NkOAuth2Template(String clientId, String clientSecret,
			String authorizeUrl, String authenticateUrl, String accessTokenUrl) {

		super(clientId, clientSecret, authorizeUrl, authenticateUrl,
				accessTokenUrl);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.accessTokenUrl = accessTokenUrl;

	}

	@Override
	public AccessGrant exchangeForAccess(String authorizationCode,
			String redirectUri,
			MultiValueMap<String, String> additionalParameters) {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("code", authorizationCode);
		params.set("redirect_uri", redirectUri);
		params.set("grant_type", "authorization_code");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return getForAccessGrant(buildUrl(accessTokenUrl, params),
				new LinkedMultiValueMap<String, String>());
	}

	@SuppressWarnings("unchecked")
	protected AccessGrant getForAccessGrant(String accessTokenUrl,
			MultiValueMap<String, String> parameters) {
		return extractAccessGrant(getRestTemplate().getForObject(
				accessTokenUrl, Map.class, parameters));
	}

	private String buildUrl(String url, MultiValueMap<String, String> parameters) {
		StringBuilder urlBuilder = new StringBuilder(url);
		for (Iterator<Entry<String, List<String>>> additionalParams = parameters
				.entrySet().iterator(); additionalParams.hasNext();) {
			Entry<String, List<String>> param = additionalParams.next();
			String name = formEncode(param.getKey());
			for (Iterator<String> values = param.getValue().iterator(); values
					.hasNext();) {
				if (urlBuilder.indexOf("?") >= 0) {
					urlBuilder.append('&').append(name).append('=')
							.append(values.next());
				} else {
					urlBuilder.append('?').append(name).append('=')
							.append(values.next());
				}
			}
		}
		return urlBuilder.toString();
	}

	private String formEncode(String data) {
		try {
			return URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// should not happen, UTF-8 is always supported
			throw new IllegalStateException(ex);
		}
	}

	private AccessGrant extractAccessGrant(Map<String, Object> result) {
		return createAccessGrant((String) result.get("access_token"),
				(String) result.get("scope"),
				(String) result.get("refresh_token"),
				getIntegerValue(result, "expires_in"), result);
	}

	private Integer getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Integer.valueOf(String.valueOf(map.get(key)));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
