package com.turkcell.turkcellcrm.customerService.core.configuration;

import com.turkcell.crm.core.config.BaseSecurityService;
import lombok.RequiredArgsConstructor;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        baseSecurityService.configureCoreSecurity(http);

        http.authorizeHttpRequests((req) -> req
                .requestMatchers("/swagger-ui/**","/customerservice/api/v1/**").permitAll());
        //.anyRequest().authenticated());
        return http.build();
    }
}
