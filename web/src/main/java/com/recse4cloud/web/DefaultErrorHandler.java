package com.recse4cloud.web;

import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.common.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 默认异常处理
 */
@ControllerAdvice
public class DefaultErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultErrorHandler.class);

    /**
     * 异常拦截
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handler(Exception e) {
        logger.error(e.getMessage(), e);
        if (e instanceof ServiceException) {
            ServiceException ex = (ServiceException) e;
            return ServiceResultHelper.result(ex.getCode(), ex.getMsg(), ex.getData());
        }
        return ServiceResultHelper.result("50000", "服务异常，请联系客服", null);
    }

    @ResponseBody
    @ExceptionHandler(NoSuchMethodException.class)
    public Object handlerNoMethod(Exception e) {
        logger.error(e.getMessage(), e);
        return ServiceResultHelper.result("50001", "接口不支持", null);
    }
}
