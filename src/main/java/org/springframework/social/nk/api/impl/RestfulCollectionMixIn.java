package org.springframework.social.nk.api.impl;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public abstract class RestfulCollectionMixIn {
 
    @JsonCreator
    public RestfulCollectionMixIn(@JsonProperty("entry") List<?> entry){}
}
