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
import org.springframework.social.nk.http.converter.json.NkJacksonMessageConverter;
import org.springframework.social.nk.oauth.consumer.client.NkCoreOAuthConsumerSupport;
import org.springframework.social.nk.oauth.consumer.client.NkOAuthClientHttpRequestFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.NkPerson;

/**
 */
public class NkTemplate extends AbstractOAuth2ApiBinding implements Nk {

    /**
     * Field accessToken.
     */
    private String accessToken;
    /**
     * Field oauthConsumerSupport.
     */
    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;
    /**
     * Field unsecureRestTemplate.
     */
    private RestTemplate unsecureRestTemplate;

    /**
     * Field abusesOperations.
     */
    private AbusesOperations<AbusesTemplate> abusesOperations;
    /**
     * Field activityOperations.
     */
    private ActivityOperations<ActivityTemplate> activityOperations;
    /**
     * Field albumOperations.
     */
    private AlbumOperations<AlbumTemplate> albumOperations;
    /**
     * Field applicationOperations.
     */
    private ApplicationOperations<ApplicationTemplate> applicationOperations;
    /**
     * Field commentOperations.
     */
    private CommentOperations<CommentTemplate> commentOperations;
    /**
     * Field giftOperations.
     */
    private GiftOperations<GiftTemplate> giftOperations;
    /**
     * Field groupOperations.
     */
    private GroupOperations<GroupTemplate> groupOperations;
    /**
     * Field mediaItemOperations.
     */
    private MediaItemOperations<MediaItemTemplate> mediaItemOperations;
    /**
     * Field messageOperations.
     */
    private MessageOperations<MessageTemplate> messageOperations;
    /**
     * Field paymentOperations.
     */
    private PaymentOperations<PaymentTemplate> paymentOperations;
    /**
     * Field peopleOperations.
     */
    private PeopleOperations<PeopleTemplate> peopleOperations;
    /**
     * Field rateOperations.
     */
    private RateOperations<RateTemplate> rateOperations;
    /**
     * Field recommendOperations.
     */
    private RecommendOperations<RecommendTemplate> recommendOperations;
    /**
     * Field publicOperations.
     */
    private PublicOperations publicOperations;

    /**
     * Constructor for NkTemplate.
     * @param accessToken String
     * @param resource ProtectedResourceDetails
     */
    public NkTemplate(String accessToken, ProtectedResourceDetails resource) {
        super();
        this.accessToken = accessToken;
        this.oauthConsumerSupport = new NkCoreOAuthConsumerSupport(resource);
        setOAuthSecurityContext(resource);
        getRestTemplate().setRequestFactory(
                new NkOAuthClientHttpRequestFactory(getRestTemplate().getRequestFactory(), resource,
                        oauthConsumerSupport));
        getRestTemplate().setMessageConverters(getMessageConverters());
        unsecureRestTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
        initSubApis();
    }

    /**
     * Method isAuthorized.
     * @return boolean
     * @see org.springframework.social.ApiBinding#isAuthorized()
     */
    public boolean isAuthorized() {
        return this.accessToken != null;
    }

    /**
     * Method getUserProfile.
     * @return NkPerson
     */
    public NkPerson getUserProfile() {
        return peopleOperations().getCurrentUserProfile();
    }
    
    /**
     * Method getUnsecureRestTemplate.
     * @return RestTemplate
     */
    public RestTemplate getUnsecureRestTemplate() {
        return unsecureRestTemplate;
    }

    /**
     * Method abusesOperations.
     * @return AbusesOperations<AbusesTemplate>
     * @see org.springframework.social.nk.api.Nk#abusesOperations()
     */
    public AbusesOperations<AbusesTemplate> abusesOperations() {
        return this.abusesOperations;
    }

    /**
     * Method activityOperations.
     * @return ActivityOperations<ActivityTemplate>
     * @see org.springframework.social.nk.api.Nk#activityOperations()
     */
    public ActivityOperations<ActivityTemplate> activityOperations() {
        return this.activityOperations;
    }

    /**
     * Method albumOperations.
     * @return AlbumOperations<AlbumTemplate>
     * @see org.springframework.social.nk.api.Nk#albumOperations()
     */
    public AlbumOperations<AlbumTemplate> albumOperations() {
        return this.albumOperations;
    }

    /**
     * Method applicationOperations.
     * @return ApplicationOperations<ApplicationTemplate>
     * @see org.springframework.social.nk.api.Nk#applicationOperations()
     */
    public ApplicationOperations<ApplicationTemplate> applicationOperations() {
        return this.applicationOperations;
    }

    /**
     * Method commentOperations.
     * @return CommentOperations<CommentTemplate>
     * @see org.springframework.social.nk.api.Nk#commentOperations()
     */
    public CommentOperations<CommentTemplate> commentOperations() {
        return this.commentOperations;
    }

    /**
     * Method giftOperations.
     * @return GiftOperations<GiftTemplate>
     * @see org.springframework.social.nk.api.Nk#giftOperations()
     */
    public GiftOperations<GiftTemplate> giftOperations() {
        return this.giftOperations;
    }

    /**
     * Method groupOperations.
     * @return GroupOperations<GroupTemplate>
     * @see org.springframework.social.nk.api.Nk#groupOperations()
     */
    public GroupOperations<GroupTemplate> groupOperations() {
        return this.groupOperations;
    }

    /**
     * Method mediaItemOperations.
     * @return MediaItemOperations<MediaItemTemplate>
     * @see org.springframework.social.nk.api.Nk#mediaItemOperations()
     */
    public MediaItemOperations<MediaItemTemplate> mediaItemOperations() {
        return this.mediaItemOperations;
    }

    /**
     * Method messageOperations.
     * @return MessageOperations<MessageTemplate>
     * @see org.springframework.social.nk.api.Nk#messageOperations()
     */
    public MessageOperations<MessageTemplate> messageOperations() {
        return this.messageOperations;
    }

    /**
     * Method paymentOperations.
     * @return PaymentOperations<PaymentTemplate>
     * @see org.springframework.social.nk.api.Nk#paymentOperations()
     */
    public PaymentOperations<PaymentTemplate> paymentOperations() {
        return this.paymentOperations;
    }

    /**
     * Method peopleOperations.
     * @return PeopleOperations<PeopleTemplate>
     * @see org.springframework.social.nk.api.Nk#peopleOperations()
     */
    public PeopleOperations<PeopleTemplate> peopleOperations() {
        return this.peopleOperations;
    }

    /**
     * Method rateOperations.
     * @return RateOperations<RateTemplate>
     * @see org.springframework.social.nk.api.Nk#rateOperations()
     */
    public RateOperations<RateTemplate> rateOperations() {
        return this.rateOperations;
    }

    /**
     * Method recommendOperations.
     * @return RecommendOperations<RecommendTemplate>
     * @see org.springframework.social.nk.api.Nk#recommendOperations()
     */
    public RecommendOperations<RecommendTemplate> recommendOperations() {
        return this.recommendOperations;
    }

    /**
     * Method publicOperations.
     * @return PublicOperations
     * @see org.springframework.social.nk.api.Nk#publicOperations()
     */
    public PublicOperations publicOperations() {
        return this.publicOperations;
    }

    /**
     * Method getJsonMessageConverter.
     * @return MappingJacksonHttpMessageConverter
     */
    @Override
    protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
        MappingJacksonHttpMessageConverter converter = new NkJacksonMessageConverter(this.oauthConsumerSupport);
        converter.getObjectMapper().registerModule(new NkModule());
        return converter;
    }
    
    /**
     * Method getFormMessageConverter.
     * @return FormHttpMessageConverter
     */
    @Override
    protected FormHttpMessageConverter getFormMessageConverter() {
        FormHttpMessageConverter converter = new NkFormHttpMessageConverter(oauthConsumerSupport);
        converter.setCharset(Charset.forName("UTF-8"));
        converter.addPartConverter(getJsonMessageConverter());
        return converter;
    }
    
    /**
     * Method requireAuthorization.
     */
    protected void requireAuthorization() {
        if (!isAuthorized()) {
            throw new MissingAuthorizationException();
        }
    }

    /**
     * Method initSubApis.
     */
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
        this.publicOperations = new PublicTemplate(getUnsecureRestTemplate());
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