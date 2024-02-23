package com.seonghun.module_product_service.domain.product.dao;

import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    ProductStock findProductStockByName(String name);

    @Modifying
    @Query("UPDATE ProductStock SET stock = :stock WHERE name = :name")
    void updateStockByName(@Param("name") String name, @Param("stock") long stock);
}
