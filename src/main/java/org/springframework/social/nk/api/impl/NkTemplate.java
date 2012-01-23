package org.springframework.social.nk.api.impl;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.nk.api.AbusesOperations;
import org.springframework.social.nk.api.ActivityOperations;
import org.springframework.social.nk.api.AlbumOperations;
import org.springframework.social.nk.api.ApplicationOperations;
import org.springframework.social.nk.api.CommentOperations;
import org.springframework.social.nk.api.GiftOperations;
import org.springframework.social.nk.api.GroupOperations;
import org.springframework.social.nk.api.MediaItemOperations;
import org.springframework.social.nk.api.MessageOperations;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.PaymentOperations;
import org.springframework.social.nk.api.PeopleOperations;
import org.springframework.social.nk.api.PublicOperations;
import org.springframework.social.nk.api.RateOperations;
import org.springframework.social.nk.api.RecommendOperations;
import org.springframework.social.nk.http.converter.json.NkFormHttpMessageConverter;
import org.springframework.social.nk.http.converter.json.TypeReferenceJacksonMessageConverter;
import org.springframework.social.nk.oauth.consumer.client.NkCoreOAuthConsumerSupport;
import org.springframework.social.nk.oauth.consumer.client.NkOAuthClientHttpRequestFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import pl.nk.opensocial.model.NkPerson;

@SuppressWarnings("rawtypes")
public class NkTemplate extends AbstractOAuth2ApiBinding implements Nk {

    private String accessToken;
    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;

    private AbusesOperations abusesOperations;
    private ActivityOperations<ActivityTemplate> activityOperations;
    private AlbumOperations<AlbumTemplate> albumOperations;
    private ApplicationOperations<ApplicationTemplate> applicationOperations;
    private CommentOperations commentOperations;
    private GiftOperations giftOperations;
    private GroupOperations groupOperations;
    private MediaItemOperations<MediaItemTemplate> mediaItemOperations;
    private MessageOperations messageOperations;
    private PaymentOperations paymentOperations;
    private PeopleOperations<PeopleTemplate> peopleOperations;
    private RateOperations rateOperations;
    private RecommendOperations recommendOperations;
    private PublicOperations publicOperations;

    public NkTemplate(String accessToken, ProtectedResourceDetails resource) {
        super();
        this.accessToken = accessToken;
        this.oauthConsumerSupport = new NkCoreOAuthConsumerSupport(resource);
        setOAuthSecurityContext(resource);
        getRestTemplate().setRequestFactory(
                new NkOAuthClientHttpRequestFactory(getRestTemplate().getRequestFactory(), resource,
                        oauthConsumerSupport));
        getRestTemplate().setMessageConverters(getMessageConverters());
        initSubApis();
    }

    public boolean isAuthorized() {
        return this.accessToken != null;
    }

    public NkPerson getUserProfile() {
        return peopleOperations().getCurrentUserProfile();
    }

    public AbusesOperations abusesOperations() {
        return this.abusesOperations;
    }

    public ActivityOperations<ActivityTemplate> activityOperations() {
        return this.activityOperations;
    }

    public AlbumOperations<AlbumTemplate> albumOperations() {
        return this.albumOperations;
    }

    public ApplicationOperations applicationOperations() {
        return this.applicationOperations;
    }

    public CommentOperations commentOperations() {
        return this.commentOperations;
    }

    public GiftOperations giftOperations() {
        return this.giftOperations;
    }

    public GroupOperations groupOperations() {
        return this.groupOperations;
    }

    public MediaItemOperations<MediaItemTemplate> mediaItemOperations() {
        return this.mediaItemOperations;
    }

    public MessageOperations messageOperations() {
        return this.messageOperations;
    }

    public PaymentOperations paymentOperations() {
        return this.paymentOperations;
    }

    public PeopleOperations<PeopleTemplate> peopleOperations() {
        return this.peopleOperations;
    }

    public RateOperations rateOperations() {
        return this.rateOperations;
    }

    public RecommendOperations recommendOperations() {
        return this.recommendOperations;
    }

    public PublicOperations publicOperations() {
        return this.publicOperations;
    }

    @Override
    protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
        MappingJacksonHttpMessageConverter converter = new TypeReferenceJacksonMessageConverter(this.oauthConsumerSupport);
        converter.getObjectMapper().registerModule(new NkModule());
        return converter;
    }
    
    @Override
    protected FormHttpMessageConverter getFormMessageConverter() {
        FormHttpMessageConverter converter = new NkFormHttpMessageConverter(oauthConsumerSupport);
        converter.setCharset(Charset.forName("UTF-8"));
        converter.addPartConverter(getJsonMessageConverter());
        return converter;
    }

    protected void requireAuthorization() {
        if (!isAuthorized()) {
            throw new MissingAuthorizationException();
        }
    }

    private final void initSubApis() {
        this.abusesOperations = new AbusesTemplate(getRestTemplate(), isAuthorized());
        this.activityOperations = new ActivityTemplate(getRestTemplate(), isAuthorized());
        this.albumOperations = new AlbumTemplate(getRestTemplate(), isAuthorized());
        this.applicationOperations = new ApplicationTemplate(getRestTemplate(), isAuthorized());
        this.commentOperations = new CommentTemplate(getRestTemplate(), isAuthorized());
        this.giftOperations = new GiftTemplate(getRestTemplate(), isAuthorized());
        this.groupOperations = new GroupTemplate(getRestTemplate(), isAuthorized());
        this.mediaItemOperations = new MediaItemTemplate(getRestTemplate(), isAuthorized());
        this.messageOperations = new MessageTemplate(getRestTemplate(), isAuthorized());
        this.paymentOperations = new PaymentTemplate(getRestTemplate(), isAuthorized());
        this.peopleOperations = new PeopleTemplate(getRestTemplate(), isAuthorized());
        this.rateOperations = new RateTemplate(getRestTemplate(), isAuthorized());
        this.recommendOperations = new RecommendTemplate(getRestTemplate(), isAuthorized());
        this.publicOperations = new PublicTemplate(getRestTemplate());
    }

    /**
     * Required by NkOAuthClientHttpRequestFactory.
     * 
     * @param resource
     */
    private final void setOAuthSecurityContext(ProtectedResourceDetails resource) {
        OAuthConsumerToken token = new OAuthConsumerToken();
        token.setAccessToken(true);
        token.setResourceId(resource.getId());
        token.setValue(accessToken);

        Map<String, OAuthConsumerToken> accessTokens = new TreeMap<String, OAuthConsumerToken>();
        accessTokens.put(resource.getId(), token);

        OAuthSecurityContextImpl context = new OAuthSecurityContextImpl();
        context.setAccessTokens(accessTokens);
        OAuthSecurityContextHolder.setContext(context);
    }

}