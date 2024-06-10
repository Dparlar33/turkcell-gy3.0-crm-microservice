package com.turkcell.identityService.core.configuration;

import com.turkcell.crm.core.config.BaseSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        http.authorizeHttpRequests(

                (req)->req.requestMatchers("/swagger-ui/**","/identityservice/api/v1/**").permitAll()

        );
        return http.build();
    }
}