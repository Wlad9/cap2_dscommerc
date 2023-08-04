package com.cap2.dscommerce.services;

import com.cap2.dscommerce.dto.ProductDTO;
import com.cap2.dscommerce.entities.Product;
import com.cap2.dscommerce.repositories.ProductRepository;
import com.cap2.dscommerce.services.exceptions.DatabaseException;
import com.cap2.dscommerce.services.exceptions.ResurceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findBayId(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResurceNotFoundException("Recurso não encontrado"));
        return new ProductDTO(product);
    }

//    @Transactional(readOnly = true)
//    public ProductDTO findBayId(Long id) {
//        Optional<Product> result = repository.findById(id);
//        Product product = result.get();
//        ProductDTO dto = new ProductDTO(product);
//        return dto;
//        //* Product product=repository.findById().get();
//        // return new ProductDTO(product);
//    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }
//    @Transactional(readOnly = true)
//    public List<ProductDTO> findAll(){
//        List<Product> result = repository.findAll();
//        return result.stream().map(x-> new ProductDTO(x)).toList();
//    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResurceNotFoundException ("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResurceNotFoundException("Recurso não encotrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referêncial!");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
