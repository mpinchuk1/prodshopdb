package org.appMain.services;

import org.appMain.entities.CustomUser;
import org.appMain.entities.dto.UserDTO;
import org.appMain.repo.UserRepository;
import org.appMain.utils.UserAlreadyExistsException;
import org.appMain.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        return userRepository.findById(id).orElse(new CustomUser());
    }

    @Transactional
    public void addUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByLogin(userDTO.getLogin()))
            throw new UserAlreadyExistsException();

        String password = userDTO.getPassword();
        String passHash = passwordEncoder.encode(password);

        CustomUser user = new CustomUser(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getLogin(), passHash, userDTO.getEmail(), UserRole.USER);
        userRepository.save(user);
    }
}
