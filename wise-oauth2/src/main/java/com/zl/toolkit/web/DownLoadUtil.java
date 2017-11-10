package com.zl.toolkit.web;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;


/**
 *  文件下载相关工具类
 *  @author axiao(xj08.dream@gmail.com)
 *  @date 2016-05-18
 *  @version 1.0
 *  Histroy: [修改人][时间][描述]
 */
public class DownLoadUtil {

    static final Logger LOGGER = LoggerFactory.getLogger(DownLoadUtil.class);

    /**
     * 设置下载文件的编码，兼容大部分浏览器，不确保兼容所有
     * @param fileName 文件名
     * @param userAgent HTTP 请求的用户代理头的值
     * @return
     * @throws Exception
     */
    public static String encodeDownloadName(String fileName,String userAgent) throws Exception {
        //针对IE或者以IE为内核的浏览器：
        if (userAgent.toUpperCase().contains("MSIE")||userAgent.toUpperCase().contains("TRIDENT")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        }
        fileName = StringUtils.deleteWhitespace(fileName);
        return fileName;
    }

    /**
     * 统一文件下载的一级文件名，以用户真实名字-当前日期时间时分秒
     * @param userRealName 当前登录用户的真实名字
     * @param userAgent HTTP 请求的用户代理头的值
     * @return
     */
    public static String downLoadName(String userRealName, String userAgent){
        try{
            return encodeDownloadName(userRealName, userAgent);// +"-"+ DateUtil.DateToStringYMDHMS(new Date())
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }

}
