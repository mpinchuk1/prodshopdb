package org.appMain.config;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Product;
import org.appMain.entities.Supplier;
import org.appMain.repo.UserRepository;
import org.appMain.services.ProductService;
import org.appMain.services.SupplierService;
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
                                  final SupplierService supplierService,
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

            Supplier supplier1 = new Supplier("Petrov", "Co company");
            Supplier supplier2 = new Supplier("Ivanov", "SupplyCom");

            Product p1 = new Product("water", 20d, new Date(2133333423423L), supplier1);
            Product p2 = new Product("apple", 12d, new Date(213333), supplier2);
            Product p3 = new Product("orange", 34d, new Date(2133333423423L), supplier1);
            Product p4 = new Product("cookie", 54d, new Date(3333312423423L), supplier2);
            Product p5 = new Product("pill", 212d, new Date(2123333423423L), supplier2);
            Product p6 = new Product("bread", 2d, new Date(3121333333333L), supplier2);
            Product p7 = new Product("cola", 1111d, new Date(1133333333213L), supplier1);
            Product p8 = new Product("sprite", 167d, new Date(1133333333213L), supplier1);
            Product p9 = new Product("fanta", 1d, new Date(3133333333213L), supplier1);

            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            List<Product> toStorage = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);
            List<Integer> prodQuantities = Arrays.asList(4, 5, 1, 46, 2, 88, 7, 22, 3);

            supplierService.addCourier(supplier1);
            supplierService.addCourier(supplier2);
            for (Product p : toStorage) {
                p.setDeliveryDate(currentDate);
                productService.addProduct(p);
            }
            supplierService.addProductsToStorage(toStorage, prodQuantities);
        };
    }
}
