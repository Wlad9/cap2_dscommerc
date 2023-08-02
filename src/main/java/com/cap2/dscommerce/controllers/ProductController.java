package com.cap2.dscommerce.controllers;

import com.cap2.dscommerce.dto.ProductDTO;
import com.cap2.dscommerce.entities.Product;
import com.cap2.dscommerce.repositories.ProductRepository;
import com.cap2.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService service;
    @GetMapping(value = "/{id}")
    public ProductDTO teste(@PathVariable Long id){
        ProductDTO dto = service.findBayId(id);
        return dto;
    }
}
