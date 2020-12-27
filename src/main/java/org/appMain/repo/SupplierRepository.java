package org.appMain.repo;

import org.appMain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s FROM Supplier s WHERE s.supplierCompanyName = :supplierCompanyName")
    Supplier findBySupplierCompanyName(@Param("supplierCompanyName") String supplierCompanyName);
}
