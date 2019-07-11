package com.recse4cloud.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {

    @Autowired(required = false)
    private SwaggerConfigurer swaggerConfigurer;

    @Bean
    @ConditionalOnMissingBean(SwaggerConfigurer.class)
    public SwaggerConfigurer swaggerConfigurer() {
        return new DefaultSwaggerConfigurer();
    }

    @Bean
    @ConditionalOnBean(SwaggerConfigurer.class)
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(swaggerConfigurer.title())
                        .contact(new Contact(swaggerConfigurer.contactName(), swaggerConfigurer.contactUrl(), swaggerConfigurer.contactEmail()))
                        .version(swaggerConfigurer.version()).description(swaggerConfigurer.description()).build())
                .select().apis(RequestHandlerSelectors.basePackage(swaggerConfigurer.basePackage())).paths(PathSelectors.any()).build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "swagger-ui.html");
        registry.addRedirectViewController("", "swagger-ui.html");
        registry.addRedirectViewController("/index", "swagger-ui.html");
    }

}
