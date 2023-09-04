package com.zl.wise.common.rpc.utils;

import java.util.Map;

public class MapBodyResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(MapBodyResponseUtils.class);

    public MapBodyResponseUtils() {
    }

    public static <K, V> MapResultResponse<K, V> codeMapResultResponse(Map<K, V> result, SystemCode systemCode, String businessCode, String message) {
        MapResultResponse<K, V> rr = new MapResultResponse();
        rr.setSystemCode(systemCode);
        rr.setBusinessCode(businessCode);
        rr.setMessage(message);
        rr.setResult(result);
        return rr;
    }

    public static <K, V> MapResultResponse<K, V> successMapResultResponse(Map<K, V> result) {
        return codeMapResultResponse(result, SystemCode.SUCCESS, BusinessCode.SUCCESS.name(), (String)null);
    }

    public static <K, V> MapResultResponse<K, V> failureMapResultResponse(String message) {
        return codeMapResultResponse((Map)null, SystemCode.FAILURE, (String)null, message);
    }
}
