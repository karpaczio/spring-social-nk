package org.springframework.social.nk.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 */
public class NkOAuth2Template extends OAuth2Template {

    /**
     * Field LOGGER.
     */
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(NkOAuth2Template.class);

    /**
     * Field clientId.
     */
    private final String clientId;

    /**
     * Field clientSecret.
     */
    private final String clientSecret;

    /**
     * Field accessTokenUrl.
     */
    private final String accessTokenUrl;

    /**
     * Constructor for NkOAuth2Template.
     * @param clientId String
     * @param clientSecret String
     * @param authorizeUrl String
     * @param accessTokenUrl String
     */
    public NkOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        this(clientId, clientSecret, authorizeUrl, null, accessTokenUrl);
    }

    /**
     * Constructor for NkOAuth2Template.
     * @param clientId String
     * @param clientSecret String
     * @param authorizeUrl String
     * @param authenticateUrl String
     * @param accessTokenUrl String
     */
    public NkOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl,
            String accessTokenUrl) {

        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;

    }

    /**
     * Method exchangeForAccess.
     * @param authorizationCode String
     * @param redirectUri String
     * @param additionalParameters MultiValueMap<String,String>
     * @return AccessGrant
     * @see org.springframework.social.oauth2.OAuth2Operations#exchangeForAccess(String, String, MultiValueMap<String,String>)
     */
    @SuppressWarnings("javadoc")
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
            MultiValueMap<String, String> additionalParameters) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("client_id", this.clientId);
        params.set("client_secret", this.clientSecret);
        params.set("code", authorizationCode);
        params.set("redirect_uri", redirectUri);
        params.set("grant_type", "authorization_code");
        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        return getForAccessGrant(buildUrl(this.accessTokenUrl, params), new LinkedMultiValueMap<String, String>());
    }

    /**
     * Method getForAccessGrant.
     * @param accessTokenUrl String
     * @param parameters MultiValueMap<String,String>
     * @return AccessGrant
     */
    @SuppressWarnings("unchecked")
    protected AccessGrant getForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        return extractAccessGrant(getRestTemplate().getForObject(accessTokenUrl, Map.class, parameters));
    }

    /**
     * Method buildUrl.
     * @param url String
     * @param parameters MultiValueMap<String,String>
     * @return String
     */
    private String buildUrl(String url, MultiValueMap<String, String> parameters) {
        StringBuilder urlBuilder = new StringBuilder(url);
        for (Iterator<Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams
                .hasNext();) {
            Entry<String, List<String>> param = additionalParams.next();
            String name = formEncode(param.getKey());
            for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
                if (urlBuilder.indexOf("?") >= 0) {
                    urlBuilder.append('&').append(name).append('=').append(values.next());
                } else {
                    urlBuilder.append('?').append(name).append('=').append(values.next());
                }
            }
        }
        return urlBuilder.toString();
    }

    /**
     * Method formEncode.
     * @param data String
     * @return String
     */
    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Method extractAccessGrant.
     * @param result Map<String,Object>
     * @return AccessGrant
     */
    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"),
                (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
    }

    /**
     * Method getIntegerValue.
     * @param map Map<String,Object>
     * @param key String
     * @return Integer
     */
    private Integer getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Integer.valueOf(String.valueOf(map.get(key)));
        } catch (NumberFormatException e) {
            LOGGER.debug("{} is not an integer. ", map.get(key));
            return null;
        }
    }
}
