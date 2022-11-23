package com.diallo.restfull.applicationventes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diallo.restfull.applicationventes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByUserId(Integer userId);
	
	Optional<Product> findByIdAndUserId(Integer id, Integer userId);

}
