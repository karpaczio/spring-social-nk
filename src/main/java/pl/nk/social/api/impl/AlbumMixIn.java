package pl.nk.social.api.impl;

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

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = AlbumImpl.class)
public abstract class AlbumMixIn {

    /**
     * Field description.
     */
    @JsonProperty
    private String description;
    
    /**
     * Field id.
     */
    @JsonProperty
    private String id;
    
    /**
     * Field mediaItemCount.
     */
    @JsonProperty
    private Integer mediaItemCount;
    
    /**
     * Field mediaMimeType.
     */
    @JsonProperty
    @JsonDeserialize(as = ArrayList.class)
    private List<String> mediaMimeType;
    
    /**
     * Field mediaType.
     */
    @JsonProperty
    private List<ApplicationMediaItem.Type> mediaType;
    
    /**
     * Field ownerId.
     */
    @JsonProperty
    private String ownerId;
    
    /**
     * Field thumbnailUrl.
     */
    @JsonProperty
    private String thumbnailUrl;
    
    /**
     * Field title.
     */
    @JsonProperty
    private String title;
}
