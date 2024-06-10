package com.turkcell.identityService;

import com.turkcell.crm.core.annotation.EnableSecurity;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableSecurity
public class IdentityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityServiceApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
