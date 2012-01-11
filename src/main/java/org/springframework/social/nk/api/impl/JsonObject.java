package org.springframework.social.nk.api.impl;

public class JsonObject<T> {

	T entry;
	
	public T getEntry() {
		return entry;
	}

	public void setEntry(T entry) {
		this.entry = entry;
	}

}
