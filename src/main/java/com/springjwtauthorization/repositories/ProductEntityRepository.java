package com.springjwtauthorization.repositories;

import com.springjwtauthorization.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
}