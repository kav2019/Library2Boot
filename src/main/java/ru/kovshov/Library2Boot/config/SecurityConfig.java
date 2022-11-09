package ru.kovshov.Library2Boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kovshov.Library2Boot.models.Permition;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetalSeviceImpl;

    @Autowired
    public SecurityConfig(@Qualifier("userDetalSeviceImpl") UserDetailsService userDetalSeviceImpl) {
        this.userDetalSeviceImpl = userDetalSeviceImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .antMatchers("/api/**").permitAll()

                .antMatchers(HttpMethod.GET, "/library/**").hasAuthority(Permition.READ.getPermition())
                .antMatchers(HttpMethod.GET, "/book/**").hasAuthority(Permition.READ.getPermition())
                .antMatchers(HttpMethod.PATCH, "/library/**").hasAuthority(Permition.WRITE.getPermition())
                .antMatchers(HttpMethod.POST, "/library/**").hasAuthority(Permition.WRITE.getPermition())
                .antMatchers(HttpMethod.PATCH, "/book/**").hasAuthority(Permition.WRITE.getPermition())
                .antMatchers(HttpMethod.POST, "/book/**").hasAuthority(Permition.WRITE.getPermition())

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/library")
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login");;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetalSeviceImpl);
        return daoAuthenticationProvider;
    }


}


