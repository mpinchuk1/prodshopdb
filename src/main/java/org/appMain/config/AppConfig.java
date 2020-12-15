package org.appMain.config;

import org.appMain.entities.CustomUser;
import org.appMain.repo.UserRepository;
import org.appMain.utils.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final UserRepository userRepository,
                                  final PasswordEncoder encoder) {
        return (strings) -> {

            CustomUser user1 = new CustomUser("Maksym", "Pinchuk", "mpinchuk", encoder.encode("qwerty"), "mmpinchuk@gmail.com", UserRole.ADMIN);
            CustomUser user2 = new CustomUser("Ilia267", "Minchuk", "iminchuk", encoder.encode("qwerty"), "iminchuk@gmail.com", UserRole.USER);
            CustomUser user3 = new CustomUser("Andr", "Andr", "andrandr", encoder.encode("qwerty"), "andr@gmail.com", UserRole.USER);
            CustomUser user4 = new CustomUser("Sasha", "Sashkevich", "ssashkevich", encoder.encode("qwerty"), "ssashkevich@gmail.com", UserRole.USER);
            CustomUser user5 = new CustomUser("Maksym", "Kapolin", "mkapolin", encoder.encode("qwerty"), null, UserRole.USER);
            CustomUser user6 = new CustomUser("Nikita", "Nikitin", "nnikitin", encoder.encode("qwerty"), "nnikitin@gmail.com", UserRole.USER);
            CustomUser user7 = new CustomUser("Denis", "Lopatin", "dlopatin", encoder.encode("qwerty"), "dlopatin@gmail.com", UserRole.USER);

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
