package org.springframework.social.nk.api.impl;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("rawtypes")
public abstract class AbstractOpenSearchOperations<F extends AbstractOpenSearchOperations> implements
        CommonOpenSocialOperations<F> {

    protected int count = 20;
    protected int startIndex = 0;
    protected Collection<?> fields = Collections.emptySet();

    @SuppressWarnings("unchecked")
    @Override
    public F setCount(int count) {
        this.count = count;
        return (F) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public F setFields(Collection<?> fields) {
        this.fields = fields;
        return (F) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public F setStartIndex(int startIndex) {
        this.startIndex = startIndex;
        return (F) this;
    }

}
