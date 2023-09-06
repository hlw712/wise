package com.zl.wise.common.concept.time;

import java.util.Date;

public class TimeScopeUtil {

    /**
     * 组装 前闭后闭 TimeScope对象
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static TimeScope toTimeScope(Date startTime, Date endTime) {
        TimeScope timeScope = new TimeScope();
        timeScope.setFrom(TimePoint.builder().inclusion(PointInclusion.INCLUSIVE).time(startTime).build());
        timeScope.setTo(TimePoint.builder().inclusion(PointInclusion.INCLUSIVE).time(startTime).build());
        return timeScope;
    }

    /**
     * 校验两个时间段是否有交集
     *
     * @param source
     * @param input
     * @return ture：有交集，false：无交集
     */
    public static boolean isInclude(BaseTimeScope source, BaseTimeScope input) {
        return (((source.getFrom().getTime().compareTo(input.getFrom().getTime())) <= 0 && source.getTo().getTime().compareTo(input.getTo().getTime()) >= 0)
                || (source.getFrom().getTime().compareTo(input.getTo().getTime()) <= 0 && source.getTo().getTime().compareTo(input.getTo().getTime()) >= 0)
                || ((input.getFrom().getTime().compareTo(source.getFrom().getTime()) <= 0 && input.getTo().getTime().compareTo(source.getFrom().getTime()) >= 0)
                || (input.getFrom().getTime().compareTo(source.getTo().getTime()) <= 0 && input.getTo().getTime().compareTo(source.getTo().getTime()) >= 0)));
    }
}
