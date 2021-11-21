package com.example.train6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/leaders/**").hasAnyRole("MANAGER")
                .antMatchers("/").hasAnyRole("EMPLOYEE")
                .antMatchers("/systems/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authUser")
                .passwordParameter("pass")
                .usernameParameter("user")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var user = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(user.username("dd2").password("1234").roles("EMPLOYEE"))
                .withUser(user.username("dd3").password("1234").roles("EMPLOYEE", "MANAGER"))
                .withUser(user.username("dd4").password("1234").roles("EMPLOYEE", "ADMIN"));
    }
}
