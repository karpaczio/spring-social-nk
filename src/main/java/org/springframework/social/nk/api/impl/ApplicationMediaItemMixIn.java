package org.springframework.social.nk.api.impl;

import org.apache.shindig.social.opensocial.model.Address;
import org.apache.shindig.social.opensocial.model.Album;
import org.apache.shindig.social.opensocial.model.MediaItem.Type;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.nk.opensocial.model.ApplicationMediaItemImpl;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = ApplicationMediaItemImpl.class)
@JsonSerialize(include = Inclusion.NON_NULL)
public class ApplicationMediaItemMixIn {

    /**
     * Field id.
     */
    @JsonProperty("id")
    private String id;

    /**
     * Field albumId.
     */
    @JsonProperty("albumId")
    private String albumId;

    /**
     * Field numVotes.
     */
    @JsonProperty("numVotes")
    private String numVotes;

    /**
     * Field rating.
     */
    @JsonProperty("rating")
    private String rating;

    /**
     * Field created.
     */
    @JsonProperty("created")
    private String created;

    /**
     * Field url.
     */
    @JsonProperty("url")
    private String url;

    /**
     * Field thumbnailUrl.
     */
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    /**
     * Field type.
     */
    @JsonProperty("type")
    private Type type;

    /**
     * Field mimeType.
     */
    @JsonProperty("mimeType")
    private String mimeType;

    /**
     * Field addedBy.
     */
    @JsonProperty("nk_addedBy")
    private String addedBy;

    /**
     * Field description.
     */
    @JsonProperty("description")
    private String description;

    /**
     * Field numSuperVotes.
     */
    @JsonProperty("nkNumSuperVotes")
    private String numSuperVotes;

    /**
     * Field nkCreatedTime.
     */
    @JsonProperty("nkCreatedTime")
    private Long nkCreatedTime;

    /**
     * Field nkVotesSum.
     */
    @JsonProperty("nkVotesSum")
    private Long nkVotesSum;

    /**
     * Field nkAlbum.
     */
    @JsonProperty("nkAlbum")
    private Album nkAlbum;

}
