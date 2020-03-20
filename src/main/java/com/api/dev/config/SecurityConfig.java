package com.api.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Classe de configuração de segurança da aplicação
 * @author Maria Rayane Alves (mrayanealves)
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/fragmentos/**", "/imagens/**").permitAll().anyRequest()
                .permitAll().and().formLogin().and().httpBasic().disable();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
