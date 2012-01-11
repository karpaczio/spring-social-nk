package org.springframework.social.nk.oauth.consumer.client;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.AccessTokenRequiredException;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthClientHttpRequestFactory;
import org.springframework.social.nk.oauth.common.NkOAuthConsumerParameter;

public class NkOAuthClientHttpRequestFactory extends
		OAuthClientHttpRequestFactory {

	private final ClientHttpRequestFactory delegate;
	private final ProtectedResourceDetails resource;
	private final OAuthConsumerSupport support;

	public NkOAuthClientHttpRequestFactory(ClientHttpRequestFactory delegate,
			ProtectedResourceDetails resource, OAuthConsumerSupport support) {
		super(delegate, resource, support);
		this.delegate = delegate;
		this.resource = resource;
		this.support = support;
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod)
			throws IOException {
		
		 OAuthSecurityContext context = OAuthSecurityContextHolder.getContext();
		    if (context == null) {
		      throw new IllegalStateException("No OAuth security context has been established. Unable to access resource '" + this.resource.getId() + "'.");
		    }

		    Map<String, OAuthConsumerToken> accessTokens = context.getAccessTokens();
		    OAuthConsumerToken accessToken = accessTokens == null ? null : accessTokens.get(this.resource.getId());
		    if (accessToken == null) {
		      throw new AccessTokenRequiredException("No OAuth security context has been established. Unable to access resource '" + this.resource.getId() + "'.", resource);
		    }

		    boolean useAuthHeader = this.resource.isAcceptsAuthorizationHeader();
		    if (!useAuthHeader) {
		      String queryString = this.support.getOAuthQueryString(this.resource, null, uri.toURL(), httpMethod.name(), this.getAdditionalOAuthParameters());
		      String uriValue = String.valueOf(uri);
		      uri = URI.create(uriValue.contains("?") ? uriValue + "&" + queryString : uriValue + "?" + queryString);
		    }
		    
		    uri = addNkToken(uri, accessToken);

		    ClientHttpRequest req = delegate.createRequest(uri, httpMethod);
		    if (useAuthHeader) {
		      String authHeader = this.support.getAuthorizationHeader(this.resource, null, uri.toURL(), httpMethod.name(), this.getAdditionalOAuthParameters());
		      req.getHeaders().add("Authorization", authHeader);
		    }

		    Map<String, String> additionalHeaders = this.resource.getAdditionalRequestHeaders();
		    if (additionalHeaders != null) {
		      for (Map.Entry<String, String> header : additionalHeaders.entrySet()) {
		        req.getHeaders().add(header.getKey(), header.getValue());
		      }
		    }
		    return req;
	}
	
	private URI addNkToken(URI uri, OAuthConsumerToken requestToken) {
		if ((requestToken != null) && (requestToken.getValue() != null)) {
			String uriValue = String.valueOf(uri);
			String queryString = NkOAuthConsumerParameter.nk_token.toString() + "=" + requestToken.getValue();
		      return URI.create(uriValue.contains("?") ? uriValue + "&" + queryString : uriValue + "?" + queryString);
		    
	}
		return uri;
	}
}
