package com.seonghun.module_product_service.domain.product.dao;

import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    ProductStock findProductStockByName(String name);
    ProductStock findProductStockById(Long id);
}
