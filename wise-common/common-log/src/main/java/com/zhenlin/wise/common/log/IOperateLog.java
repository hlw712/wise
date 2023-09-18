package com.zhenlin.wise.common.log;

/**
 * 业务操作日志枚举
 *
 * @author huanglinwei
 * @date 2020/8/14 9:36 PM
 * @version 1.0.0
 */
public interface IOperateLog {

    String getUrl();

    String getOperation();

    String getDesc();

    String[] getFields();

}