package pl.nk.social.api.impl;

import java.util.Collection;
import java.util.Collections;

/**
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractOpenSearchOperations<F extends AbstractOpenSearchOperations> implements
        CommonOpenSocialOperations<F> {

    /**
     * Field count.
     */
    protected int count = 20;
    /**
     * Field startIndex.
     */
    protected int startIndex = 0;
    /**
     * Field fields.
     */
    protected Collection<?> fields = Collections.emptySet();

    /**
     * Method setCount.
     * @param count int
     * @return F
     * @see pl.nk.social.api.impl.CommonOpenSocialOperations#setCount(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public F setCount(int count) {
        this.count = count;
        return (F) this;
    }

    /**
     * Method setFields.
     * @param fields Collection<?>
     * @return F
     * @see org.springframework.social.nk.api.impl.CommonOpenSocialOperations#setFields(Collection<?>)
     */
    @SuppressWarnings({ "unchecked", "javadoc" })
    @Override
    public F setFields(Collection<?> fields) {
        this.fields = fields;
        return (F) this;
    }

    /**
     * Method setStartIndex.
     * @param startIndex int
     * @return F
     * @see pl.nk.social.api.impl.CommonOpenSocialOperations#setStartIndex(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public F setStartIndex(int startIndex) {
        this.startIndex = startIndex;
        return (F) this;
    }

}
