package ru.kovshov.Library2Boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kovshov.Library2Boot.service.UserDetalSevice;

import java.util.Collections;

@Component
public class AuthProvaiderImpl implements AuthenticationProvider {
    private final UserDetalSevice userDetalSevice;

    @Autowired
    public AuthProvaiderImpl(UserDetalSevice userDetalSevice) {
        this.userDetalSevice = userDetalSevice;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetalSevice.loadUserByUsername(name);
        if (!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password,
                Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
