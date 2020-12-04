package org.appMain.config;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Role;
import org.appMain.repo.RoleRepository;
import org.appMain.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner demo(final RoleRepository roleRepository, final UserRepository userRepository) {
        return (strings) -> {

            Role admin = new Role("ADMIN");
            Role user = new Role("USER");

            roleRepository.save(admin);
            roleRepository.save(user);

            CustomUser user1 = new CustomUser("Maksym", "Pinchuk", "mpinchuk", "qwerty", "mmpinchuk@gmail.com", admin);
            CustomUser user2 = new CustomUser("Ilia267", "Minchuk", "iminchuk", "qwerty", "iminchuk@gmail.com", null);
            CustomUser user3 = new CustomUser("Andr", "Andr", "andrandr", "qwerty", "andr@gmail.com", user);
            CustomUser user4 = new CustomUser("Sasha", "Sashkevich", "ssashkevich", "qwerty", "ssashkevich@gmail.com", user);
            CustomUser user5 = new CustomUser("Maksym", "Kapolin", "mkapolin", "qwerty", null, user);
            CustomUser user6 = new CustomUser("Nikita", "Nikitin", "nnikitin", "qwerty", "nnikitin@gmail.com", user);
            CustomUser user7 = new CustomUser("Denis", "Lopatin", "dlopatin", "qwerty", "dlopatin@gmail.com", user);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);


        };
    }
}
