package com.zl.toolkit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xu on 2015/11/30.
 */
public class WebUtil {
    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
    
    /**
     * 设置下载文件的响应头和文件名
     * 
     * @param fileName
     */
    public static void setDownHeader(String fileName){
        HttpServletRequest request = RequestResponseContext.getRequest();
        HttpServletResponse response = RequestResponseContext.getResponse();
        
        String userAgent = request.getHeader("User-Agent");
        try {
            fileName = DownLoadUtil.encodeDownloadName(fileName, userAgent);
        } catch (Exception e) {
            fileName = "fileName";
        }
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/octet-stream");
    }
}
