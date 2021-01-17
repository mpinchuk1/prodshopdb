package org.appMain.services;

import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.appMain.entities.Supplier;
import org.appMain.entities.dto.AddProductDTO;
import org.appMain.entities.dto.ProductDTO;
import org.appMain.repo.ProductRepository;
import org.appMain.repo.StorageRepository;
import org.appMain.repo.SupplierRepository;
import org.appMain.utils.ProductAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StorageRepository storageRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, StorageRepository storageRepository,
                          SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.storageRepository = storageRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.getProductsInStock();
    }

    @Transactional
    public List<ProductDTO> getProductsWithQuantities() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            Storage storageItem = storageRepository.findByProductName(product.getName()).orElse(new Storage());
            productDTOS.add(new ProductDTO(product.getId(), product.getName(),
                    product.getPrice(), product.getDeliveryDate(),
                    product.getExpireDate(), product.getDeliveredBy(),
                    storageItem.getQuantity()));
        }
        return productDTOS;
    }

    @Transactional
    public void update(AddProductDTO productForUpdate) throws ProductAlreadyExistsException {
        int quantity = productForUpdate.getQuantity();
        Product product = productRepository.findById(productForUpdate.getId()).orElseThrow(ProductAlreadyExistsException::new);
        product.setName(productForUpdate.getName());
        product.setPrice(productForUpdate.getPrice());
        product.setExpireDate(productForUpdate.getExpireDate());
        productRepository.save(product);
        saveToStorage(product, quantity);
    }

    @Transactional
    public void addProduct(Product product) {
        if (productRepository.findProductByName(product.getName()) == null) {
            productRepository.save(product);
        }
    }

    @Transactional
    public void addProduct(AddProductDTO productToAdd) throws ProductAlreadyExistsException {

        if (productRepository.findProductByName(productToAdd.getName()) != null) {
            throw new ProductAlreadyExistsException();
        }

        Supplier supplier = supplierRepository.findBySupplierCompanyName(productToAdd.getDeliveredBy());
        Product product = new Product(productToAdd.getName(),
                productToAdd.getPrice(), productToAdd.getExpireDate(), supplier);
        int prodQuantity = productToAdd.getQuantity();

        productRepository.save(product);
        saveToStorage(product, prodQuantity);
    }

    @Transactional
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(new Product());
    }

    @Transactional
    public Optional<Storage> findStorageByProductName(String productName) {
        return storageRepository.findByProductName(productName);
    }

    public AddProductDTO getAddProductDTO(Long id) {
        Product product = findById(id);
        Storage storage = findStorageByProductName(product.getName()).orElse(new Storage());
        return new AddProductDTO(product.getId(),
                product.getName(), product.getPrice(),
                product.getExpireDate(),
                product.getDeliveredBy().getSupplierCompanyName(), storage.getQuantity());
    }

    private void saveToStorage(Product product, int quantity) {
        Storage storageItem = storageRepository.findByProductName(product.getName()).orElse(new Storage());
        storageItem.setProduct(product);
        storageItem.setQuantity(quantity);
        storageRepository.save(storageItem);
    }
}
