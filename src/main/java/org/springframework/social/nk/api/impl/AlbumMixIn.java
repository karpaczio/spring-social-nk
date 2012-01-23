package org.springframework.social.nk.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shindig.social.core.model.AlbumImpl;
import org.apache.shindig.social.opensocial.model.MediaItem;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.ser.std.EnumSerializer;

import pl.nk.opensocial.model.ApplicationMediaItem;
import pl.nk.opensocial.model.NkPersonImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = AlbumImpl.class)
public abstract class AlbumMixIn {

    @JsonProperty
    private String description;
    
    @JsonProperty
    private String id;
    
    @JsonProperty
    private Integer mediaItemCount;
    
    @JsonProperty
    @JsonDeserialize(as = ArrayList.class)
    private List<String> mediaMimeType;
    
    @JsonProperty
    private List<ApplicationMediaItem.Type> mediaType;
    
    @JsonProperty
    private String ownerId;
    
    @JsonProperty
    private String thumbnailUrl;
    
    @JsonProperty
    private String title;
}
