package com.example.WigellTravelService.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(h -> h.frameOptions(f -> f.disable()))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User
                .withUsername("simon")
                .password("{noop}simon")
                .roles("ADMIN")
                .build();

        UserDetails userOne = User
                .withUsername("amanda")
                .password("{noop}amanda")
                .roles("USER")
                .build();

        UserDetails userTwo = User
                .withUsername("sara")
                .password("{noop}sara")
                .roles("USER")
                .build();

        UserDetails userThree = User
                .withUsername("alex")
                .password("{noop}alex")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, userOne, userTwo, userThree);
    }
}
