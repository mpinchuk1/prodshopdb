package org.appMain.services;

import org.appMain.entities.Product;
import org.appMain.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.getProductsInStock();
    }

    @Transactional
    public void addProduct(Product product) {
        if (productRepository.findProductByName(product.getName()) == null) {
            productRepository.save(product);
        }
    }
}
