package org.appMain.services;

import org.appMain.entities.Courier;
import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.appMain.repo.CourierRepository;
import org.appMain.repo.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourierService {

    private final CourierRepository courierRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public CourierService(CourierRepository courierRepository, StorageRepository storageRepository) {
        this.courierRepository = courierRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void addProductsToStorage(Courier courier, List<Product> toStorage, List<Integer> prodQuantities){
        courierRepository.save(courier);

        for(int i = 0; i < toStorage.size(); i++){
            Product prodTemp = toStorage.get(i);
            int prodQTemp = prodQuantities.get(i);
            Storage existsStorageItem = storageRepository.findByProduct(prodTemp);

            if(existsStorageItem == null){
                Storage storageItem = new Storage();
                storageItem.setProduct(prodTemp);
                storageItem.setQuantity(prodQTemp);
                storageRepository.save(storageItem);
            }else {
                int productQuantity = existsStorageItem.getQuantity();
                int newProductQuantity = productQuantity + prodQTemp;
                existsStorageItem.setQuantity(newProductQuantity);
                storageRepository.save(existsStorageItem);
            }

        }
    }

    @Transactional
    public void addCourier(Courier courier){
        courierRepository.save(courier);
    }
}
