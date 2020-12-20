package org.appMain.repo;

import org.appMain.entities.Product;
import org.appMain.entities.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    @Query("SELECT s FROM Storage s WHERE s.product = :product")
    Storage findByProduct(@Param("product") Product product);
}
