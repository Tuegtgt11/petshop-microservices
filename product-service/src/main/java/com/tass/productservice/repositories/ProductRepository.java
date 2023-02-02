package com.tass.productservice.repositories;

import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findProductByBarcode(String barcode);

    List<Product> findAllByCategory(Category category);
}
