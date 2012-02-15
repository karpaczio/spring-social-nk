package pl.nk.social.api.http.converter.json;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 */
public class TypeReferenceJacksonMessageConverter extends MappingJacksonHttpMessageConverter {

    /**
     * Method canRead.
     * @param clazz Class<?>
     * @param mediaType MediaType
     * @return boolean
     * @see org.springframework.http.converter.HttpMessageConverter#canRead(Class<?>, MediaType)
     */
    @SuppressWarnings("javadoc")
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {

        if (TypeReference.class.isAssignableFrom(clazz)) {
            // if Class<TypeReference<X>> given, then check X
            Type pt = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
            return super.canRead(pt.getClass(), mediaType);
        }
        return super.canRead(clazz, mediaType);
    }

    /**
     * Method readInternal.
     * @param clazz Class<?>
     * @param inputMessage HttpInputMessage
     * @return Object
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
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
