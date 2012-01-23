package org.springframework.social.nk.http.converter.json;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.nk.oauth.consumer.client.NkCoreOAuthConsumerSupport;

public class TypeReferenceJacksonMessageConverter extends MappingJacksonHttpMessageConverter {

    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;

    public TypeReferenceJacksonMessageConverter(NkCoreOAuthConsumerSupport oauthConsumerSupport) {
        super();
        this.oauthConsumerSupport = oauthConsumerSupport;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        super.writeInternal(object, outputMessage);

        byte[] bodyAsBytes = null;
        if (object != null) {
            // TODO can we get it from outputMessage.getBody() ?
            bodyAsBytes = this.getObjectMapper().writeValueAsBytes(object);
            this.oauthConsumerSupport.setOAuthBodyHash(bodyAsBytes);
        }

        this.oauthConsumerSupport.addAuthorizationHeader(outputMessage);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {

        if (TypeReference.class.isAssignableFrom(clazz)) {
            // if Class<TypeReference<X>> given, then check X
            Type pt = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
            return super.canRead(pt.getClass(), mediaType);
        }
        return super.canRead(clazz, mediaType);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {

        try {
            if (TypeReference.class.isAssignableFrom(clazz)) {
                Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                @SuppressWarnings("rawtypes")
                TypeReference ref = (TypeReference) constructor.newInstance(new Object[] {});
                return this.getObjectMapper().readValue(inputMessage.getBody(), ref);
            }
            return this.getObjectMapper().readValue(inputMessage.getBody(), clazz);
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: ", ex);
        } catch (InvocationTargetException ite) {
            throw new HttpMessageNotReadableException("Could not create instance of TypeReference: ", ite);
        } catch (IllegalAccessException iae) {
            throw new HttpMessageNotReadableException("Could not create instance of TypeReference: ", iae);
        } catch (InstantiationException e) {
            throw new HttpMessageNotReadableException("Could not create instance of TypeReference:  ", e);
        }
    }
}
