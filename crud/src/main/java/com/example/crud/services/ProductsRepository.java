package com.example.crud.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

}
