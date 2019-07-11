package com.recse4cloud.common.core.exception;

import com.recse4cloud.common.core.ServiceResultHelper;

public class ServiceExceptionUtil {

    /**
     * 服务异常抛出
     *
     * @param code
     * @param msg
     * @param data
     * @throws ServiceException
     */
    public static void throwServiceException(String code, String msg, Object data) throws ServiceException {
        ServiceException serviceException = new ServiceException(msg);
        serviceException.setCode(code);
        serviceException.setMsg(msg);
        serviceException.setData(data);
        throw serviceException;
    }

    /**
     * 服务异常抛出
     *
     * @param code
     * @param data
     * @throws ServiceException
     */
    public static void throwServiceException(String code, Object data) throws ServiceException {
        throwServiceException(code, ServiceResultHelper.getMessage(code), data);
    }

    /**
     * 服务异常抛出
     *
     * @param code
     * @param msg
     * @throws ServiceException
     */
    public static void throwServiceException(String code, String msg) throws ServiceException {
        throwServiceException(code, msg, null);
    }

    /**
     * 服务异常抛出
     *
     * @param code
     * @throws ServiceException
     */
    public static void throwServiceException(String code) throws ServiceException {
        throwServiceException(code, ServiceResultHelper.getMessage(code));
    }
}
