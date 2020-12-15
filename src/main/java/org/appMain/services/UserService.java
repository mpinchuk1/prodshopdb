package org.appMain.services;

import org.appMain.entities.CustomUser;
import org.appMain.repo.UserRepository;
import org.appMain.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public CustomUser findByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean addUser(String firstName, String lastName, String login, String passHash, String email,
                           UserRole role) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(firstName, lastName, login, passHash, email, role);
        userRepository.save(user);
        return true;
    }
}
