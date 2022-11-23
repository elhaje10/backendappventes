package com.diallo.restfull.applicationventes.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.diallo.restfull.applicationventes.model.Product;
import com.diallo.restfull.applicationventes.repository.FileStorageRepository;
import com.diallo.restfull.applicationventes.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final FileStorageRepository fileStorageRepository;

	public ProductService(ProductRepository productRepository,
			FileStorageRepository fileStorageRepository ) {
				this.fileStorageRepository = fileStorageRepository;
				this.productRepository = productRepository;		
	}
	
	public Product saveProduct(Product product, InputStream inputStream) {
		Product prod = productRepository.save(product);
		fileStorageRepository.saveFile(prod.getFolderFileName(), inputStream);		
		return prod;		
	}
	
	public void deleteAllById(Iterable<Long> longs) {

	}
}
