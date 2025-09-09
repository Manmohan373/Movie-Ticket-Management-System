package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(request->request
    			.requestMatchers(HttpMethod.POST,"/TicketBooking/getAccessToken").permitAll()

    			.anyRequest().authenticated());
    	http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	http.oauth2ResourceServer(authServer->authServer.jwt(Customizer.withDefaults()));
    	 http.cors(Customizer.withDefaults()).csrf(csrf->csrf.disable());
      return http.build();   	
    	
    }	
}
