package com.recse4cloud.security;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * security配置
 */
@Configuration
@EnableWebSecurity
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认UserDetailsService配置
     *
     * @return
     */
    @Bean("defaultUserDetailsService")
    @ConditionalOnMissingBean(value = UserDetailsService.class)
    public UserDetailsService defaultUserDetailsService() {
        Logger.info("创建默认的 - UserDetailsService", getClass());
        return new DefaultUserDetailsService();
    }

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(parserAnnotation());
        super.init(web);
    }

    @Bean
    @ConditionalOnMissingBean(value = IHttpSecurityConfigure.class)
    public IHttpSecurityConfigure httpSecurityConfigure() {
        return new DefaultHttpSecurityConfigure();
    }

    @Autowired
    IHttpSecurityConfigure defaultHttpSecurityConfigure;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        defaultHttpSecurityConfigure.configure(http);
    }


    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    public String[] parserAnnotation() {
        Set<String> set = new LinkedHashSet<>();
        String[] defaultPath = {"", "/", "/index", "/swagger-ui.html",
                "/swagger-*/**", "/webjars/**", "/actuator/*", "/favicon.ico", "/*/api-docs"};
        set.addAll(Arrays.asList(defaultPath));
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            IgnoreAuth auth = handlerMethod.getMethod().getAnnotation(IgnoreAuth.class);
            if (auth != null && auth.value() != null && auth.value().length > 0) {
                Logger.info("--------" + JSON.toJSONString(auth.value()), getClass());
                set.addAll(Arrays.asList(auth.value()));
            }
        });
        return set.toArray(new String[set.size()]);
    }
}
