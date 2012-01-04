package org.springframework.social.nk.api.impl;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("unused")
public abstract class NkProfileMixin {

	@JsonCreator
	public NkProfileMixin(@JsonProperty String id, @JsonProperty String name) {
	}

	@JsonProperty
	private String id;

	@JsonProperty
	private String name;
}
