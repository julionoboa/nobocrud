package com.example.NoboCRUD.repository;

import com.example.NoboCRUD.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    List<Sale> findByClientName(String name);
}
