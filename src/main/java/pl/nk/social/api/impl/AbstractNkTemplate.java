package pl.nk.social.api.impl;

import static org.apache.shindig.protocol.RequestItem.COUNT;
import static org.apache.shindig.protocol.RequestItem.FIELDS;
import static org.apache.shindig.protocol.RequestItem.START_INDEX;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractNkTemplate<E extends AbstractNkTemplate> extends AbstractOpenSearchOperations<E> {

    /**
     * Field SOCIAL_REST_URL_BASE.
     * (value is ""http://opensocial.nk-net.pl/v09/social/rest"")
     */
    public static final String SOCIAL_REST_URL_BASE = "http://opensocial.nk-net.pl/v09/social/rest";
    /**
     * Field COMMON_URL_BASE.
     * (value is ""http://opensocial.nk-net.pl/v09/common"")
     */
    public static final String COMMON_URL_BASE = "http://opensocial.nk-net.pl/v09/common";
    
    /**
     * Field APPLICATION_JSON_CONTENT_TYPE.
     */
    protected static final HttpHeaders APPLICATION_JSON_CONTENT_TYPE = new HttpHeaders();
    {
        APPLICATION_JSON_CONTENT_TYPE.setContentType(MediaType.APPLICATION_JSON);
    }
    
    /**
     * Field MEDIA_ITEM_JSON_CONTENT_TYPE.
     */
    protected static final HttpHeaders MEDIA_ITEM_JSON_CONTENT_TYPE = new HttpHeaders();
    {
        MEDIA_ITEM_JSON_CONTENT_TYPE.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    /**
     * Field EMPTY_PARAMETERS.
     */
    private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();
    /**
     * Field isAuthorized.
     */
    private final boolean isAuthorized;
    /**
     * Field restTemplate.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructor for AbstractNkTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public AbstractNkTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        this.restTemplate = restTemplate;
        this.isAuthorized = isAuthorized;
    }

    /**
     * Method getRestTemplate.
     * @return RestTemplate
     */
    protected RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    /**
     * Method requireAuthorization.
     */
    protected void requireAuthorization() {
        if (!this.isAuthorized) {
            throw new MissingAuthorizationException();
        }
    }

    /**
     * Method buildUri.
     * @param path String
     * @param parameters MultiValueMap<String,String>
     * @return URI
     */
    protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
        return URIBuilder.fromUri(SOCIAL_REST_URL_BASE + path).queryParams(parameters).build();
    }

    /**
     * Method getWithFieldsCountStartIndex.
     * @param path String
     * @param typeReference TypeReference<T>
     * @return T
     */
    protected <T> T getWithFieldsCountStartIndex(String path, TypeReference<T> typeReference) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        if (fields != null && !fields.isEmpty()) {
            parameters.set(FIELDS.toString(), StringUtils.join(fields, ","));
        }
        parameters.set(START_INDEX.toString(), String.valueOf(startIndex));
        parameters.set(COUNT.toString(), String.valueOf(count));

        return get(path, typeReference, parameters);
    }

    /**
     * Method getWithFields.
     * @param path String
     * @param typeReference TypeReference<T>
     * @return T
     */
    protected <T> T getWithFields(String path, TypeReference<T> typeReference) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        if (fields != null && !fields.isEmpty()) {
            parameters.set(FIELDS.toString(), StringUtils.join(fields, ","));
        }

        return get(path, typeReference, parameters);
    }

    /**
     * Method getForObject.
     * @param path String
     * @param typeReference TypeReference<T>
     * @return T
     */
    protected <T> T getForObject(String path, TypeReference<T> typeReference) {
        return get(path, typeReference, EMPTY_PARAMETERS);
    }

    /**
     * Method get.
     * @param path String
     * @param typeReference TypeReference<T>
     * @param parameters MultiValueMap<String,String>
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected <T> T get(String path, TypeReference<T> typeReference, MultiValueMap<String, String> parameters) {
        requireAuthorization();
        Object obj = this.getRestTemplate().getForObject(buildUri(path, parameters), typeReference.getClass());
        return (T) obj;
    }

    /**
     * Method post.
     * @param path String
     * @param request Object
     * @param typeReference TypeReference<T>
     * @return T
     */
    protected <T> T post(String path, Object request, TypeReference<T> typeReference) {
        return post(path, EMPTY_PARAMETERS, request, typeReference);
    }

    /**
     * Method post.
     * @param path String
     * @param parameters MultiValueMap<String,String>
     * @param request Object
     * @param typeReference TypeReference<T>
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected <T> T post(String path, MultiValueMap<String, String> parameters, Object request,
            TypeReference<T> typeReference) {
        requireAuthorization();
        Object obj = this.getRestTemplate().postForObject(buildUri(path, parameters), request,
                typeReference.getClass());
        return (T) obj;

    }

}
