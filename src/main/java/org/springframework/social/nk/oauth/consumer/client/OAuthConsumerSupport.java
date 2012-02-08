package org.springframework.social.nk.oauth.consumer.client;

import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_consumer_key;

import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_nonce;
import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_signature;
import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_signature_method;
import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_timestamp;
import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_token;
import static org.springframework.security.oauth.common.OAuthConsumerParameter.oauth_version;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthRequestFailedException;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.CoreOAuthConsumerSupport;

/**
 * Splits collection of oauth parameters to several functions.
 * @author luruski
 *
 * @version $Revision: 1.0 $
 */
public class OAuthConsumerSupport extends CoreOAuthConsumerSupport {

    /**
     * Method loadOAuthParameters.
     * @param details ProtectedResourceDetails
     * @param requestURL URL
     * @param requestToken OAuthConsumerToken
     * @param httpMethod String
     * @param additionalParameters Map<String,String>
     * @return Map<String,Set<CharSequence>>
     */
    @Override
    protected Map<String, Set<CharSequence>> loadOAuthParameters(ProtectedResourceDetails details, URL requestURL,
            OAuthConsumerToken requestToken, String httpMethod, Map<String, String> additionalParameters) {

        String tokenSecret = requestToken == null ? null : requestToken.getSecret();

        Map<String, Set<CharSequence>> oauthParameters = getOAuthParameters(details, requestURL, requestToken, additionalParameters);
        String signatureBaseString = getSignatureBaseString(oauthParameters, requestURL, httpMethod);
        OAuthSignatureMethod signatureMethod;
        
        try {
            signatureMethod = getSignatureFactory().getSignatureMethod(details.getSignatureMethod(),
                    details.getSharedSecret(), tokenSecret);
        } catch (UnsupportedSignatureMethodException e) {
            throw new OAuthRequestFailedException(e.getMessage(), e);
        }
        
        String signature = signatureMethod.sign(signatureBaseString);
        oauthParameters.put(oauth_signature.toString(), Collections.singleton((CharSequence) signature));
        return oauthParameters;
    }

    /**
     * Method getOAuthParameters.
     * @param details ProtectedResourceDetails
     * @param requestURL URL
     * @param requestToken OAuthConsumerToken
     * @param additionalParameters Map<String,String>
     * @return Map<String,Set<CharSequence>>
     */
    protected Map<String, Set<CharSequence>> getOAuthParameters(ProtectedResourceDetails details, URL requestURL,
            OAuthConsumerToken requestToken, Map<String, String> additionalParameters) {
        Map<String, Set<CharSequence>> oauthParams = new TreeMap<String, Set<CharSequence>>();

        oauthParams.putAll(getOAuthParametersFromAdditionalParameters(additionalParameters));
        oauthParams.putAll(getOAuthParametersFromQuery(requestURL));

        oauthParams.put(oauth_consumer_key.toString(), Collections.singleton((CharSequence) details.getConsumerKey()));
        if ((requestToken != null) && (requestToken.getValue() != null)) {
            oauthParams.put(oauth_token.toString(), Collections.singleton((CharSequence) requestToken.getValue()));
        }
        String nonce = getNonceFactory().generateNonce();
        oauthParams.put(oauth_nonce.toString(), Collections.singleton((CharSequence) nonce));
        oauthParams.put(oauth_signature_method.toString(), Collections.singleton((CharSequence) details.getSignatureMethod()));
        oauthParams.put(oauth_timestamp.toString(), Collections.singleton((CharSequence) String.valueOf(System.currentTimeMillis() / 1000)));
        oauthParams.put(oauth_version.toString(), Collections.singleton((CharSequence) "1.0"));
        
        oauthParams.putAll(getExtraOAuthParameters(details, requestURL, requestToken));

        return oauthParams;
    }

    /**
     * Method getOAuthParametersFromAdditionalParameters.
     * @param additionalParameters Map<String,String>
     * @return Map<String,Set<CharSequence>>
     */
    protected Map<String, Set<CharSequence>> getOAuthParametersFromAdditionalParameters(
            Map<String, String> additionalParameters) {
        Map<String, Set<CharSequence>> oauthParams = new TreeMap<String, Set<CharSequence>>();
        if (additionalParameters != null) {
            for (Map.Entry<String, String> additionalParam : additionalParameters.entrySet()) {
                Set<CharSequence> values = oauthParams.get(additionalParam.getKey());
                if (values == null) {
                    values = new HashSet<CharSequence>();
                    oauthParams.put(additionalParam.getKey(), values);
                }
                if (additionalParam.getValue() != null) {
                    values.add(additionalParam.getValue());
                }
            }
        }
        return oauthParams;
    }

    /**
     * Method getOAuthParametersFromQuery.
     * @param requestURL URL
     * @return Map<String,Set<CharSequence>>
     */
    protected Map<String, Set<CharSequence>> getOAuthParametersFromQuery(URL requestURL) {
        Map<String, Set<CharSequence>> oauthParams = new TreeMap<String, Set<CharSequence>>();
        String query = requestURL.getQuery();
        if (query != null) {
            StringTokenizer queryTokenizer = new StringTokenizer(query, "&");
            while (queryTokenizer.hasMoreElements()) {
                String token = (String) queryTokenizer.nextElement();
                CharSequence value = null;
                int equalsIndex = token.indexOf('=');
                if (equalsIndex < 0) {
                    token = urlDecode(token);
                } else {
                    value = new QueryParameterValue(urlDecode(token.substring(equalsIndex + 1)));
                    token = urlDecode(token.substring(0, equalsIndex));
                }

                Set<CharSequence> values = oauthParams.get(token);
                if (values == null) {
                    values = new HashSet<CharSequence>();
                    oauthParams.put(token, values);
                }
                if (value != null) {
                    values.add(value);
                }
            }
        }
        return oauthParams;
    }
    
    /**
     * Method getExtraOAuthParameters.
     * @param details ProtectedResourceDetails
     * @param requestURL URL
     * @param requestToken OAuthConsumerToken
     * @return Map<String,Set<CharSequence>>
     */
    protected Map<String, Set<CharSequence>> getExtraOAuthParameters(ProtectedResourceDetails details, URL requestURL,
            OAuthConsumerToken requestToken) {
        return Collections.emptyMap();
    }
}
