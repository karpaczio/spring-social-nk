package pl.nk.social.api.impl;

import java.util.Collection;

/**
 */
public interface CommonOpenSocialOperations<T> {

    /**
     * Method setCount.
     * @param count int
     * @return T
     */
    T setCount(int count);

    /**
     * Method setStartIndex.
     * @param startIndex int
     * @return T
     */
    T setStartIndex(int startIndex);

    /**
     * Method setFields.
     * @param fields Collection<?>
     * @return T
     */
    T setFields(Collection<?> fields);
}
