package com.recse4cloud.web.swagger;

public class DefaultSwaggerConfigurer implements SwaggerConfigurer {
    /**
     * 标题
     *
     * @return
     */
    @Override
    public String title() {
        return "微服务API";
    }

    /**
     * 版本
     *
     * @return
     */
    @Override
    public String version() {
        return "1.0.0";
    }

    /**
     * 描述
     *
     * @return
     */
    @Override
    public String description() {
        return "微服务API";
    }

    /**
     * 扫描基目录
     *
     * @return
     */
    @Override
    public String basePackage() {
        return "com.*.controller";
    }

    /**
     * 联系人姓名
     *
     * @return
     */
    @Override
    public String contactName() {
        return "杨工";
    }

    /**
     * 联系URL
     *
     * @return
     */
    @Override
    public String contactUrl() {
        return "www.xxxx.com";
    }

    /**
     * 联系Email
     *
     * @return
     */
    @Override
    public String contactEmail() {
        return null;
    }
}
