package com.Badhwar.journalApp.config;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.UserRepository;
import com.Badhwar.journalApp.services.UserDetailsServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    //Implementation of how the user will be fetched from the DB and how it will Map with UserDetails Object
//    @Autowired
//    private UserDetailsServicesImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    //Custom Security Filter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServices()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsServices()
    {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUserName(username);
                if(user != null)
                {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(user.getUserName())
                            .password(user.getPassword())
                            .roles(user.getRoles().toArray(new String[0]))
                            .build();
                }
                throw new UsernameNotFoundException("User not found with username: "+username);
            }
        };
    }

    //Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
