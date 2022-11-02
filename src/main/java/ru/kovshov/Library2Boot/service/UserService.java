package ru.kovshov.Library2Boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kovshov.Library2Boot.models.Role;
import ru.kovshov.Library2Boot.models.Status;
import ru.kovshov.Library2Boot.models.User;
import ru.kovshov.Library2Boot.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean searchUserInDB(User user){ //находим юзеа в бд, если есть возвращавем TRUE
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isEmpty()){
            return false;
        }
        return true;
    }

    private String codingPassword(String passwordFromForm){
        return passwordEncoder.encode(passwordFromForm);
    }

    @Transactional
    public boolean saveNewUser(User user){
        if(searchUserInDB(user)){
            return false;
        }
        user.setPassword(codingPassword(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return true;
    }
}
