package org.appMain.repo;

import org.appMain.entities.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    @Query("SELECT s FROM Storage s WHERE s.product.name = :product")
    Optional<Storage> findByProductName(@Param("product") String productName);
}
