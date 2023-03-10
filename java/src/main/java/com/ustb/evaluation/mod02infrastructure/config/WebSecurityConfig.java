package com.ustb.evaluation.mod02infrastructure.config;

import com.ustb.evaluation.mod02infrastructure.filter.JwtAuthorizationFilter;
import com.ustb.evaluation.mod02infrastructure.filter.JwtLoginFilter;
import com.ustb.evaluation.mod02infrastructure.prop.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    private SysUserService sysUserService;
    @Autowired
    private RsaKeyProperties prop;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //  daoAuthenticationProvider.setUserDetailsService(sysUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public void configure(HttpSecurity http) throws Exception {
        //??????csrf????????????
        http.csrf().disable();
        //??????cors????????????
        http.cors().disable();
        //??????session??????
        http.sessionManagement().disable();
        //??????form????????????
        http.formLogin().disable();
        //????????????????????????????????????????????????????????????
      //  http.addFilter(new JwtLoginFilter(super.authenticationManager(), prop));//???????????????????????????????????????????????????token
        http.addFilter(new JwtAuthorizationFilter(super.authenticationManager(), prop));


    }
}