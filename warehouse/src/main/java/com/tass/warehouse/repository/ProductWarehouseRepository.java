package com.tass.warehouse.repository;

import com.tass.warehouse.entities.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Long> {
}
