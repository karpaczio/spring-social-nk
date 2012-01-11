package org.springframework.social.nk.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NkProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String displayName;

	public NkProfile() {
	}

	public NkProfile(String id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

}
