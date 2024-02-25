package com.seonghun.module_order_service.domain.product.dao;

import com.seonghun.module_order_service.domain.product.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
