package com.example.NoboCRUD.repository;

import com.example.NoboCRUD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findAllByName(@Param("name") String name);

}
