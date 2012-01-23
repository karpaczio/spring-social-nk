package org.springframework.social.nk.api.impl;

import java.util.Collection;

public interface CommonOpenSocialOperations<T> {

    T setCount(int count);

    T setStartIndex(int startIndex);

    T setFields(Collection<?> fields);
}
