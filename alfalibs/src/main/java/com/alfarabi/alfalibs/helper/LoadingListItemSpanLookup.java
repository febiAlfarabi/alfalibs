package com.alfarabi.alfalibs.helper;

/** SpanSizeLookup that will be used to determine the span of loading list item. */
public interface LoadingListItemSpanLookup {

    /** @return the span of loading list item. */
    int getSpanSize();
}