package org.appMain.config;

import org.appMain.entities.Courier;
import org.appMain.entities.CustomUser;
import org.appMain.entities.Product;
import org.appMain.repo.UserRepository;
import org.appMain.services.CourierService;
import org.appMain.services.ProductService;
import org.appMain.utils.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final UserRepository userRepository,
                                  final CourierService courierService,
                                  final ProductService productService,
                                  final PasswordEncoder encoder) {
        return (strings) -> {

            CustomUser user1 = new CustomUser("Maksym", "Pinchuk", "mpinchuk", encoder.encode("qwerty"), "mmpinchuk@gmail.com", UserRole.ADMIN);
            CustomUser user2 = new CustomUser("Ilia267", "Minchuk", "iminchuk", encoder.encode("qwerty"), "iminchuk@gmail.com", UserRole.USER);
            CustomUser user3 = new CustomUser("Andr", "Andr", "andrandr", encoder.encode("qwerty"), "andr@gmail.com", UserRole.USER);
            CustomUser user4 = new CustomUser("Sasha", "Sashkevich", "ssashkevich", encoder.encode("qwerty"), "ssashkevich@gmail.com", UserRole.USER);
            CustomUser user5 = new CustomUser("Maksym", "Kapolin", "mkapolin", encoder.encode("qwerty"), null, UserRole.USER);
            CustomUser user6 = new CustomUser("Nikita", "Nikitin", "nnikitin", encoder.encode("qwerty"), "nnikitin@gmail.com", UserRole.USER);
            CustomUser user7 = new CustomUser("Denis", "Lopatin", "dlopatin", encoder.encode("qwerty"), "dlopatin@gmail.com", UserRole.USER);

            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6, user7));

            Courier courier1 = new Courier("Petrov", "Co company");

            Product p1 = new Product("water", 20d, new Date(2133333423423L), courier1, false);
            Product p2 = new Product("apple", 12d, new Date(213333), courier1, false);
            Product p3 = new Product("orange", 34d, new Date(2133333423423L), courier1, false);
            Product p4 = new Product("cookie", 54d, new Date(3333312423423L), courier1, false);
            Product p5 = new Product("pill", 212d, new Date(2123333423423L), courier1, false);
            Product p6 = new Product("bread", 2d, new Date(3121333333333L), courier1, false);
            Product p7 = new Product("cola", 1111d, new Date(1133333333213L), courier1, false);
            Product p8 = new Product("bear", 11123121d, new Date(1133333333213L), courier1, true);
            Product p9 = new Product("vodka", 1d, new Date(3133333333213L), courier1, true);

            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            List<Product> toStorage = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);
            List<Integer> prodQuantities = Arrays.asList(4, 5, 1, 46, 2, 88, 7, 22, 3);

            courierService.addCourier(courier1);
            for (Product p : toStorage) {
                p.setDeliveryDate(currentDate);
                productService.addProduct(p);
            }
            courierService.addProductsToStorage(toStorage, prodQuantities);
        };
    }
}
