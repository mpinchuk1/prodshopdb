package org.appMain.services;

import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.appMain.repo.ProductRepository;
import org.appMain.repo.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

    private final ProductRepository productRepository;
    private final StorageRepository storageRepository;
    private final Date currentDate;

    @Autowired
    public ShopService(ProductRepository productRepository, StorageRepository storageRepository) {
        this.productRepository = productRepository;
        this.storageRepository = storageRepository;
        Calendar calendar = Calendar.getInstance();
        this.currentDate = calendar.getTime();
    }

    @Transactional
    public void filterExpiredProducts() {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.getExpireDate().before(this.currentDate)) {
                logger.info("There is an expired product: " + p + ". \n It will be disposed of!");
                Optional<Storage> storage = storageRepository.findByProductName(p.getName());
                storage.ifPresent(storageRepository::delete);
            }
        }
    }
}
