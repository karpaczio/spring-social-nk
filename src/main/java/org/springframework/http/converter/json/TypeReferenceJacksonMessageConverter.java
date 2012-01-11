package org.springframework.http.converter.json;

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

public class TypeReferenceJacksonMessageConverter extends
		MappingJacksonHttpMessageConverter {

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if (TypeReference.class.isAssignableFrom(clazz)) {
	        Type pt = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
			return super.canRead(pt.getClass(), mediaType);
		}
		return super.canRead(clazz, mediaType);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		try {
			if (TypeReference.class.isAssignableFrom(clazz)) {
				
				Constructor c = clazz.getDeclaredConstructors()[0];
				c.setAccessible(true);
				@SuppressWarnings("rawtypes")
				TypeReference ref = (TypeReference) c.newInstance(new Object[]{});
				return this.getObjectMapper().readValue(inputMessage.getBody(), ref);
			}
			return this.getObjectMapper().readValue(inputMessage.getBody(), clazz);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: ", ex);
		} catch (InvocationTargetException ite) {
			throw new HttpMessageNotReadableException("Could not read JSON: ", ite);
		} catch (IllegalAccessException iae) {
			throw new HttpMessageNotReadableException("Could not read JSON: ", iae);
		} catch (IllegalArgumentException e) {
			throw new HttpMessageNotReadableException("Could not read JSON: ", e);
		} catch (InstantiationException e) {
			throw new HttpMessageNotReadableException("Could not read JSON: ", e);
		} catch (SecurityException e) {
		} 
		throw new HttpMessageNotReadableException("Could not read JSON: ");
	}
}
