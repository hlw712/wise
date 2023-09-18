package com.zhenlin.wise.common.log;

import com.uaepay.basis.beacon.common.util.JsonUtil;
import com.uaepay.merchant.api.facade.log.request.ApplyOperateLogRequest;
import com.uaepay.merchant.api.model.Staff;
import com.uaepay.merchant.console.frontend.common.util.JsonUtils;
import com.uaepay.merchant.console.frontend.ext.integration.OperateLogClient;
import com.uaepay.merchant.console.frontend.web.security.SecurityUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 操作日志拦截
 *
 * @author huanglinwei
 * @version 1.0.0
 * @date 2020/8/13 11:39 PM
 */
@Aspect
@Slf4j
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogClient operateLogClient;

    @Pointcut("@annotation(com.zl.wise.common.log.OperateLog)")
    public void pointcut() {
    }

    @After(value = "pointcut()")
    public void process(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        HashMap map = new HashMap();
        OperateLog operateLog = AnnotationUtils.findAnnotation(((MethodSignature) joinPoint.getSignature()).getMethod(),  OperateLog.class);
        OperateLogEnum operateLogEnum = operateLog.logType();
        try {
            if (args != null && args.length > 0 && !OperateLogEnum.LOGOUT.equals(operateLogEnum)) {
                String objStr = JsonUtils.toJsonString(args[0]);
                if (!StringUtils.isEmpty(objStr)){
                    map = JsonUtil.parseObject(objStr, HashMap.class);
                }
            }
        } catch (Exception e) {
            log.error("operate Log JSON analysis exception", e);
        }

        try {
            String requestParamValue = (operateLogEnum.getRemark() == null ? "" : operateLogEnum.getRemark()) + this.getParamValue(operateLogEnum, map);
            String subParamValue = OperateLogEnum.COMMON_AUDIT.equals(operateLogEnum) ? map.get("auditStatus")+" " : "";
            Staff staff = SecurityUserHolder.get().getStaff();
            CompletableFuture.runAsync(() -> {
                ApplyOperateLogRequest applyOperateLogRequest = new ApplyOperateLogRequest();
                applyOperateLogRequest.setStaffMid(staff.getStaffMid());
                applyOperateLogRequest.setStaffName(staff.getName());
                applyOperateLogRequest.setMerchantMid(staff.getMerchant().getMid());
                applyOperateLogRequest.setOperationTime(new Date());
                applyOperateLogRequest.setRemark(subParamValue + requestParamValue);
                applyOperateLogRequest.setOperation(operateLogEnum.getOperation());
                try {
                    operateLogClient.apply(applyOperateLogRequest);
                } catch (Exception e) {
                    log.error("call operate Log exception", e);
                }
            });
        } catch (Exception e) {
            log.error("operate Log JSON analysis exception", e);
        }

    }

    private String getParamValue(OperateLogEnum operateLogEnum, Map map) {
        if (operateLogEnum.getFields() != null) {
            for (String field : operateLogEnum.getFields()) {
                return map.get(field)+"";
            }
        }
        return "";
    }
}
