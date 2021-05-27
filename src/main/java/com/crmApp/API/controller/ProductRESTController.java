package com.crmApp.API.controller;

import com.crmApp.API.repository.ProductRepository;
import com.crmApp.API.model.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product-management", produces = { MediaType.APPLICATION_JSON_VALUE})
public class ProductRESTController 
{
    @Autowired
    private ProductRepository repository;

    //Getters and Setters

    public ProductRepository getRepository() {
        return repository;
    }

    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/products")
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @PostMapping("/products")
    Product createOrSaveProduct(@RequestBody Product newProduct) {
        return repository.save(newProduct);
    }
 
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable Long id) {
        return repository.findById(id).get();
    }
 
    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
 
        return repository.findById(id).map(Product -> {
            Product.setName(newProduct.getName());
            Product.setDescription(newProduct.getDescription());
            Product.setPrice(newProduct.getPrice());
            Product.setStock(newProduct.getStock());
            return repository.save(Product);
        }).orElseGet(() -> {
            newProduct.setProductId(id);
            return repository.save(newProduct);
        });
    }
 
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

