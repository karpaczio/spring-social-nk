package pl.nk.social.api.impl;

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
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.NkPerson;
import pl.nk.social.api.AbusesOperations;
import pl.nk.social.api.ActivityOperations;
import pl.nk.social.api.AlbumOperations;
import pl.nk.social.api.ApplicationOperations;
import pl.nk.social.api.CommentOperations;
import pl.nk.social.api.GiftOperations;
import pl.nk.social.api.GroupOperations;
import pl.nk.social.api.MediaItemOperations;
import pl.nk.social.api.MessageOperations;
import pl.nk.social.api.Nk;
import pl.nk.social.api.PaymentOperations;
import pl.nk.social.api.PeopleOperations;
import pl.nk.social.api.PublicOperations;
import pl.nk.social.api.RateOperations;
import pl.nk.social.api.RecommendOperations;
import pl.nk.social.api.http.converter.json.NkFormHttpMessageConverter;
import pl.nk.social.api.http.converter.json.NkJacksonMessageConverter;
import pl.nk.social.api.oauth.consumer.client.NkCoreOAuthConsumerSupport;
import pl.nk.social.api.oauth.consumer.client.NkOAuthClientHttpRequestFactory;

/**
 */
public class NkTemplate extends AbstractOAuth2ApiBinding implements Nk {

    private static final String RESOURCE_SERVER_URL_DEFAULT = "http://opensocial.nk-net.pl"; 
    private static final String SOCIAL_SUFFIX = "/v09/social/rest";
    private static final String COMMON_SUFFIX = "/v09/common";
    
    private String socialResourceUrl = RESOURCE_SERVER_URL_DEFAULT + SOCIAL_SUFFIX;
    private String commonResourceUrl = RESOURCE_SERVER_URL_DEFAULT + COMMON_SUFFIX;
    
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
        this(accessToken, resource, null);
    }
    
    public NkTemplate(String accessToken, ProtectedResourceDetails resource, String resourceServerUrl) {
        super();
        if (resourceServerUrl != null) {
            this.socialResourceUrl = resourceServerUrl + SOCIAL_SUFFIX;
            this.commonResourceUrl = resourceServerUrl + COMMON_SUFFIX;
        }
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
     * @see pl.nk.social.api.Nk#abusesOperations()
     */
    public AbusesOperations<AbusesTemplate> abusesOperations() {
        return this.abusesOperations;
    }

    /**
     * Method activityOperations.
     * @return ActivityOperations<ActivityTemplate>
     * @see pl.nk.social.api.Nk#activityOperations()
     */
    public ActivityOperations<ActivityTemplate> activityOperations() {
        return this.activityOperations;
    }

    /**
     * Method albumOperations.
     * @return AlbumOperations<AlbumTemplate>
     * @see pl.nk.social.api.Nk#albumOperations()
     */
    public AlbumOperations<AlbumTemplate> albumOperations() {
        return this.albumOperations;
    }

    /**
     * Method applicationOperations.
     * @return ApplicationOperations<ApplicationTemplate>
     * @see pl.nk.social.api.Nk#applicationOperations()
     */
    public ApplicationOperations<ApplicationTemplate> applicationOperations() {
        return this.applicationOperations;
    }

    /**
     * Method commentOperations.
     * @return CommentOperations<CommentTemplate>
     * @see pl.nk.social.api.Nk#commentOperations()
     */
    public CommentOperations<CommentTemplate> commentOperations() {
        return this.commentOperations;
    }

    /**
     * Method giftOperations.
     * @return GiftOperations<GiftTemplate>
     * @see pl.nk.social.api.Nk#giftOperations()
     */
    public GiftOperations<GiftTemplate> giftOperations() {
        return this.giftOperations;
    }

    /**
     * Method groupOperations.
     * @return GroupOperations<GroupTemplate>
     * @see pl.nk.social.api.Nk#groupOperations()
     */
    public GroupOperations<GroupTemplate> groupOperations() {
        return this.groupOperations;
    }

    /**
     * Method mediaItemOperations.
     * @return MediaItemOperations<MediaItemTemplate>
     * @see pl.nk.social.api.Nk#mediaItemOperations()
     */
    public MediaItemOperations<MediaItemTemplate> mediaItemOperations() {
        return this.mediaItemOperations;
    }

    /**
     * Method messageOperations.
     * @return MessageOperations<MessageTemplate>
     * @see pl.nk.social.api.Nk#messageOperations()
     */
    public MessageOperations<MessageTemplate> messageOperations() {
        return this.messageOperations;
    }

    /**
     * Method paymentOperations.
     * @return PaymentOperations<PaymentTemplate>
     * @see pl.nk.social.api.Nk#paymentOperations()
     */
    public PaymentOperations<PaymentTemplate> paymentOperations() {
        return this.paymentOperations;
    }

    /**
     * Method peopleOperations.
     * @return PeopleOperations<PeopleTemplate>
     * @see pl.nk.social.api.Nk#peopleOperations()
     */
    public PeopleOperations<PeopleTemplate> peopleOperations() {
        return this.peopleOperations;
    }

    /**
     * Method rateOperations.
     * @return RateOperations<RateTemplate>
     * @see pl.nk.social.api.Nk#rateOperations()
     */
    public RateOperations<RateTemplate> rateOperations() {
        return this.rateOperations;
    }

    /**
     * Method recommendOperations.
     * @return RecommendOperations<RecommendTemplate>
     * @see pl.nk.social.api.Nk#recommendOperations()
     */
    public RecommendOperations<RecommendTemplate> recommendOperations() {
        return this.recommendOperations;
    }

    /**
     * Method publicOperations.
     * @return PublicOperations
     * @see pl.nk.social.api.Nk#publicOperations()
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
        this.abusesOperations = new AbusesTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.activityOperations = new ActivityTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.albumOperations = new AlbumTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.applicationOperations = new ApplicationTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.commentOperations = new CommentTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.giftOperations = new GiftTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.groupOperations = new GroupTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.mediaItemOperations = new MediaItemTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.messageOperations = new MessageTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.paymentOperations = new PaymentTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.peopleOperations = new PeopleTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.rateOperations = new RateTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.recommendOperations = new RecommendTemplate(getRestTemplate(), isAuthorized(), socialResourceUrl, commonResourceUrl);
        this.publicOperations = new PublicTemplate(getUnsecureRestTemplate(), socialResourceUrl, commonResourceUrl);
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