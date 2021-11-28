package com.example.train6.config;

import com.example.train6.config.handlers.LoginSuccessHandler;
import com.example.train6.config.handlers.LogoutSuccessHandler;
import com.example.train6.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService us;
    private LoginSuccessHandler loginSuccessHandler;
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/leaders/**").hasAnyAuthority("ROLE_MANAGER")
                .antMatchers("/").hasAnyAuthority("ROLE_EMPLOYEE")
                .antMatchers("/systems/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/authUser")
                .passwordParameter("pass")
                .usernameParameter("user")
                .permitAll()
                .and()
                .rememberMe().rememberMeCookieName("rem").rememberMeParameter("remember").tokenValiditySeconds(60)
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("rem", "JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        var user = User.withDefaultPasswordEncoder();
//        auth.jdbcAuthentication().dataSource(ds)
//                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery("select username, roles from authorities where username = ?");
//        auth.inMemoryAuthentication()
//                .withUser(user.username("dd2").password("1234").roles("EMPLOYEE"))
//                .withUser(user.username("dd3").password("1234").roles("EMPLOYEE", "MANAGER"))
//                .withUser(user.username("dd4").password("1234").roles("EMPLOYEE", "ADMIN"));
        auth.userDetailsService(us);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}