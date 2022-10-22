package ru.kovshov.Library2Boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kovshov.Library2Boot.security.AuthProvaiderImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProvaiderImpl authProvaider;

    @Autowired
    public SecurityConfig(AuthProvaiderImpl authProvaider) {
        this.authProvaider = authProvaider;
    }

    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authProvaider);

    }

}
