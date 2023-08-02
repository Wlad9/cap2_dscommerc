package com.cap2.dscommerce.services;

import com.cap2.dscommerce.dto.ProductDTO;
import com.cap2.dscommerce.entities.Product;
import com.cap2.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findBayId(Long id){
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product);
        return dto;
        //* Product product=repository.findById().get();
        // return new ProductDTO(product);
    }
}