package org.appMain.services;

import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.appMain.entities.Supplier;
import org.appMain.repo.StorageRepository;
import org.appMain.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, StorageRepository storageRepository) {
        this.supplierRepository = supplierRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void addProductsToStorage(List<Product> toStorage, List<Integer> prodQuantities) {

        for (int i = 0; i < toStorage.size(); i++) {
            Product prodTemp = toStorage.get(i);
            int prodQTemp = prodQuantities.get(i);
            saveToStorage(prodTemp, prodQTemp);
        }
    }

    public void saveToStorage(Product product, int quantity) {
        Storage existsStorageItem = storageRepository.findByProductName(product.getName()).orElse(null);

        if (existsStorageItem == null) {
            Storage storageItem = new Storage();
            storageItem.setProduct(product);
            storageItem.setQuantity(quantity);
            storageRepository.save(storageItem);
        } else {
            int productQuantity = existsStorageItem.getQuantity();
            int newProductQuantity = productQuantity + quantity;
            existsStorageItem.setQuantity(newProductQuantity);
            storageRepository.save(existsStorageItem);
        }
    }

    @Transactional
    public Supplier findSupplierByCompany(String company) {
        return supplierRepository.findBySupplierCompanyName(company);
    }

    @Transactional
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Transactional
    public void addCourier(Supplier supplier) {
        supplierRepository.save(supplier);
    }
}
