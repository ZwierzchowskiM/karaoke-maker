package com.karaoke.karaokemaker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
class SecurityConfig   {

    private final ObjectMapper objectMapper;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final String secret;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(ObjectMapper objectMapper, RestAuthenticationSuccessHandler successHandler,
                          RestAuthenticationFailureHandler failureHandler,
                          AuthenticationConfiguration authenticationConfiguration, @Value("${jwt.secret}") String secret,
                          CustomUserDetailsService customUserDetailsService) {
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.authenticationConfiguration = authenticationConfiguration;
        this.secret = secret;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                requests -> {
                    try {
                        requests
                                .antMatchers("/swagger-ui/**").permitAll()
                                .antMatchers("/v2/api-docs").permitAll()
                                .antMatchers("/webjars/**").permitAll()
                                .antMatchers("/swagger-resources/**").permitAll()
                                .antMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated() //AnyRequestMatcher -> AuthenticatedAuthorizationManager Wszystkie pozostałe żądania wymagają uwierzytelnienia z dowolną rolą.
                                .and()
                                .formLogin().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), customUserDetailsService, secret))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .and()
                                .headers().frameOptions().disable();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        http.csrf().disable();

        return http.build();
    }

    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {


        JsonObjectAuthenticationFilter authenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return authenticationFilter;
    }


}
