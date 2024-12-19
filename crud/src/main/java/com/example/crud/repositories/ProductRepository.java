package com.example.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    
}
