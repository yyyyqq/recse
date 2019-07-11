package com.recse4cloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;

public class DefaultHttpSecurityConfigure implements IHttpSecurityConfigure {
    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    /**
     * 配置security的认证
     *
     * @param http
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.cors().configurationSource(request-> new CorsConfiguration());
        http.headers().frameOptions().disable();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(new RestAuthenticationEntryPoint());
//        http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated()
                .and().logout().clearAuthentication(true).invalidateHttpSession(true)
                .and().formLogin().successForwardUrl("/");
    }
}
