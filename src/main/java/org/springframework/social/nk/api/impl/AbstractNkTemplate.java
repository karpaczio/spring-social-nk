package org.springframework.social.nk.api.impl;

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

@SuppressWarnings("rawtypes")
public abstract class AbstractNkTemplate<E extends AbstractNkTemplate> extends AbstractOpenSearchOperations<E> {

    public static final String API_URL_BASE = "http://opensocial.nk-net.pl/v09/social/rest";
    protected static final HttpHeaders APPLICATION_JSON_CONTENT_TYPE = new HttpHeaders();
    static {
        APPLICATION_JSON_CONTENT_TYPE.setContentType(MediaType.APPLICATION_JSON);
    }
    protected static final HttpHeaders MEDIA_ITEM_JSON_CONTENT_TYPE = new HttpHeaders();
    static {
        MEDIA_ITEM_JSON_CONTENT_TYPE.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();
    private final boolean isAuthorized;
    private final RestTemplate restTemplate;

    public AbstractNkTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        this.restTemplate = restTemplate;
        this.isAuthorized = isAuthorized;
    }

    protected RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    protected void requireAuthorization() {
        if (!this.isAuthorized) {
            throw new MissingAuthorizationException();
        }
    }

    protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
        return URIBuilder.fromUri(API_URL_BASE + path).queryParams(parameters).build();
    }

    protected <T> T getWithFieldsCountStartIndex(String path, TypeReference<T> typeReference) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        if (fields != null && !fields.isEmpty()) {
            parameters.set(FIELDS.toString(), StringUtils.join(fields, ","));
        }
        parameters.set(START_INDEX.toString(), String.valueOf(startIndex));
        parameters.set(COUNT.toString(), String.valueOf(count));

        return get(path, typeReference, parameters);
    }

    protected <T> T getWithFields(String path, TypeReference<T> typeReference) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        if (fields != null && !fields.isEmpty()) {
            parameters.set(FIELDS.toString(), StringUtils.join(fields, ","));
        }

        return get(path, typeReference, parameters);
    }

    protected <T> T getForObject(String path, TypeReference<T> typeReference) {
        return get(path, typeReference, EMPTY_PARAMETERS);
    }

    @SuppressWarnings("unchecked")
    protected <T> T get(String path, TypeReference<T> typeReference, MultiValueMap<String, String> parameters) {
        requireAuthorization();
        Object obj = this.getRestTemplate().getForObject(buildUri(path, parameters), typeReference.getClass());
        return (T) obj;
    }

    protected <T> T post(String path, Object request, TypeReference<T> typeReference) {
        return post(path, EMPTY_PARAMETERS, request, typeReference);
    }

    @SuppressWarnings("unchecked")
    protected <T> T post(String path, MultiValueMap<String, String> parameters, Object request,
            TypeReference<T> typeReference) {
        requireAuthorization();
        Object obj = this.getRestTemplate().postForObject(buildUri(path, parameters), request,
                typeReference.getClass());
        return (T) obj;

    }

}
