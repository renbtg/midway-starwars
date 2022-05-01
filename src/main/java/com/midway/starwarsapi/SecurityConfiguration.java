
package com.midway.starwarsapi;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Use this configuration in production
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors();
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().oauth2ResourceServer().jwt();
//
//        Okta.configureResourceServer401ResponseBody(http);
//    }

//    Use this configuration in dev testing to get around CORS
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }
}

