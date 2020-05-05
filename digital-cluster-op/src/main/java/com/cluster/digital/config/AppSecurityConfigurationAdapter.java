package com.cluster.digital.config;

import com.cluster.digital.component.RESTAuthenticationFailureHandler;
import com.cluster.digital.component.RESTAuthenticationSuccessHandler;
import com.cluster.digital.component.RESTLogoutSuccessHandler;
import com.cluster.digital.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    RESTAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    RESTAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    RESTLogoutSuccessHandler restLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(daoAuthenticationProvider)
                .authorizeRequests()


                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/fe/**").hasRole("FE")
                .antMatchers("/csp/**").hasRole("CSP")
                .antMatchers("/public/**").permitAll()

                .anyRequest().authenticated()

                .and().exceptionHandling()
                //.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .formLogin()
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .and()

                .cors()
                .and()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
        ;
    }

}
