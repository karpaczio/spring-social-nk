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

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = ApplicationMediaItemImpl.class)
@JsonSerialize(include = Inclusion.NON_NULL)
public class ApplicationMediaItemMixIn {

    @JsonProperty("id")
    private String id;

    @JsonProperty("albumId")
    private String albumId;

    @JsonProperty("numVotes")
    private String numVotes;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("created")
    private String created;

    @JsonProperty("url")
    private String url;

    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("mimeType")
    private String mimeType;

    @JsonProperty("nk_addedBy")
    private String addedBy;

    @JsonProperty("description")
    private String description;

    @JsonProperty("nkNumSuperVotes")
    private String numSuperVotes;

    @JsonProperty("nkCreatedTime")
    private Long nkCreatedTime;

    @JsonProperty("nkVotesSum")
    private Long nkVotesSum;

    @JsonProperty("nkAlbum")
    private Album nkAlbum;

}
