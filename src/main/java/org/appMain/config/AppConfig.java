package org.appMain.config;

import org.appMain.entities.Seller;
import org.appMain.repo.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner demo(final SellerRepository sellerRepository) {
        return strings -> {
            Seller seller1 = new Seller("Kolya", "Frolov");
            sellerRepository.save(seller1);

        };
    }
}
