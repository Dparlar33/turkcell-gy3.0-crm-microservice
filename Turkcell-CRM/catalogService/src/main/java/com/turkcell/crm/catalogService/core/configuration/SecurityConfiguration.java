package com.turkcell.crm.catalogService.core.configuration;

import com.turkcell.crm.core.config.BaseSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final BaseSecurityService baseSecurityService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        baseSecurityService.configureCoreSecurity(http);

        http.authorizeHttpRequests((req) -> req
                .requestMatchers("/swagger-ui/**","/catalogservice/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/catalogservice/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/catalogservice/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/catalogservice/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/catalogservice/api/v1/**").permitAll());
                //.anyRequest().authenticated());

        return http.build();
    }
}
