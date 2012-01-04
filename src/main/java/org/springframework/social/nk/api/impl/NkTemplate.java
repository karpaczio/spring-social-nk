package org.springframework.social.nk.api.impl;

import java.net.URI;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.NkProfile;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NkTemplate extends AbstractOAuth2ApiBinding implements Nk {

	public NkTemplate(String accessToken) {
		super(accessToken);
	}
	
	public NkProfile getUserProfile() {
		requireAuthorization();
		return getRestTemplate().getForObject(buildUri("/people/@me"), NkProfile.class);
	}
	
	protected URI buildUri(String path) {
		return buildUri(path, EMPTY_PARAMETERS);
	}
	
	protected URI buildUri(String path, String parameterName, String parameterValue) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set(parameterName, parameterValue);
		return buildUri(path, parameters);
	}
	
	protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(API_URL_BASE + path).queryParams(parameters).build();
	}
	
	protected void requireAuthorization() {
		if (!isAuthorized()) {
			throw new MissingAuthorizationException();
		}
	}
	
	private static final String API_URL_BASE = "http://java1.omega.nknet/v09/social/rest";

	private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();
}
