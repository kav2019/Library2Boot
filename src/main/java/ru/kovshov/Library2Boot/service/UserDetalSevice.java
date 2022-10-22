package ru.kovshov.Library2Boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kovshov.Library2Boot.models.User;
import ru.kovshov.Library2Boot.repository.UserRepository;
import ru.kovshov.Library2Boot.security.UserDetail;

import java.util.Optional;

@Service
public class UserDetalSevice implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetalSevice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetails = userRepository.findByUsername(username);
        if (username.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetail(userDetails.get());
    }
}
