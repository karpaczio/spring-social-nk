package org.springframework.social.nk.api.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.json.TypeReferenceJacksonMessageConverter;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.CoreOAuthConsumerSupport;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.NkProfile;
import org.springframework.social.nk.oauth.consumer.client.NkOAuthClientHttpRequestFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NkTemplate extends AbstractOAuth2ApiBinding implements Nk {

	private static final TypeReference<JsonObject<NkProfile>> typeReference = new TypeReference<JsonObject<NkProfile>>() {
	};
	
	private String accessToken;
	
	public NkTemplate(String accessToken, ProtectedResourceDetails resource) {
		
		super();
		this.accessToken = accessToken;
		OAuthSecurityContextImpl context = new OAuthSecurityContextImpl();
		Map<String, OAuthConsumerToken> accessTokens = new TreeMap<String, OAuthConsumerToken>();
		OAuthConsumerToken token = new OAuthConsumerToken();
		token.setAccessToken(true);
		token.setResourceId(resource.getId());
		token.setValue(accessToken);
		accessTokens.put(resource.getId(), token);
		context.setAccessTokens(accessTokens);
		OAuthSecurityContextHolder.setContext(context);
		getRestTemplate().setRequestFactory(new NkOAuthClientHttpRequestFactory(getRestTemplate().getRequestFactory(), resource, new CoreOAuthConsumerSupport()));
	}
	

	// implementing ApiBinding
	
	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		// TODO Auto-generated method stub
		return new TypeReferenceJacksonMessageConverter();
	}
	/*
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		TypeReferenceJacksonMessageConverter converter = new TypeReferenceJacksonMessageConverter();
		super.getMessageConverters().add(converter);
		return super.getMessageConverters();
	}
	*/
	
	public boolean isAuthorized() {
		return accessToken != null;
	}
	
	public NkProfile getUserProfile() {
		requireAuthorization();
		Object jsonObject = getRestTemplate().getForObject(buildUri("/people/@me"), typeReference.getClass());
		return ((JsonObject<NkProfile>)jsonObject).getEntry();
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
