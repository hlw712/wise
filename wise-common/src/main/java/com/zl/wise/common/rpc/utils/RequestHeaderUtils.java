package com.zl.wise.common.rpc.utils;

import com.zl.wise.common.rpc.request.Cacheable;
import com.zl.wise.common.rpc.request.RequestHeader;

public class RequestHeaderUtils {
    private RequestHeaderUtils() {
    }

    public static boolean isCacheable(RequestHeader header, Cacheable defaultCacheable) {
        Validate.isTrue(defaultCacheable == Cacheable.ENABLE || defaultCacheable == Cacheable.DISABLE, "defaultCacheable incorrect", new Object[0]);
        Cacheable cacheable = (Cacheable)ObjectUtils.defaultIfNull(header.getCacheable(), defaultCacheable);
        return cacheable == Cacheable.ENABLE;
    }
}
