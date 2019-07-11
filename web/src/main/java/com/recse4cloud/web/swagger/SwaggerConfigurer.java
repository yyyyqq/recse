package com.recse4cloud.web.swagger;

public interface SwaggerConfigurer {

    /**
     * 标题
     *
     * @return
     */
    String title();

    /**
     * 版本
     *
     * @return
     */
    String version();

    /**
     * 描述
     *
     * @return
     */
    String description();

    /**
     * 扫描基目录
     *
     * @return
     */
    String basePackage();

    /**
     * 联系人姓名
     *
     * @return
     */
    String contactName();

    /**
     * 联系URL
     *
     * @return
     */
    String contactUrl();

    /**
     * 联系Email
     *
     * @return
     */
    String contactEmail();
}
