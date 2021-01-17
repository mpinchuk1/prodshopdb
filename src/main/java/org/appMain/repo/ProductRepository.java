package org.appMain.repo;

import org.appMain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product findProductByName(@Param("name") String name);

    @Query("SELECT p FROM Product p INNER JOIN Storage s ON p.id = s.product.id WHERE s.quantity > 0")
    List<Product> getProductsInStock();
}
