package org.springframework.social.nk.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shindig.social.opensocial.model.MediaItem;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.nk.opensocial.model.NkActivityImpl;
import pl.nk.opensocial.model.NkPersonImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = NkActivityImpl.class)
@JsonSerialize(include = Inclusion.NON_NULL)
public class NkActivityMixIn {

    @JsonProperty("id")
    private String id;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("postedTime")
    private Long postedTime;

    @JsonProperty("title")
    private String title;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("nkCommentsCount")
    private Integer nkCommentsCount;

    @JsonProperty("nkStarsCount")
    private Integer nkStarsCount;

    @JsonProperty("nkStarsAuthrosIds")
    private List<String> nkStarsAuthorsIds;

    @JsonProperty("nkAppId")
    private String nkAppId;

    @JsonProperty("nkGroupId")
    private String nkGroupId;

    @JsonProperty("nkOrigins")
    private List<String> nkOrigins;

    @JsonProperty("nkImageUrl")
    private String nkImageUrl;

    @JsonProperty("nkDefaultDescription")
    private String nkDefaultDescription;

    @JsonProperty("nkSiteUrl")
    private String nkSiteUrl;

    @JsonProperty("nkSiteDescription")
    private String nkSiteDescription;

    @JsonProperty("nkSiteTitle")
    private String nkSiteTitle;

    @JsonProperty("nkSiteImageUrl")
    private String nkSiteImageUrl;

}
