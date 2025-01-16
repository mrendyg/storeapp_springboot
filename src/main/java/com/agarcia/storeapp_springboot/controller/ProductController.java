package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import com.agarcia.storeapp_springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductEntity> getListProduct(){
        return productService.getsListProduct();
    }

    @GetMapping("/{id}")
    public ProductEntity getIdProduct(@PathVariable long id){
        return productService.getIdProduct(id);
    }

    @PostMapping("/create")
    public ProductEntity createProduct(@RequestBody ProductEntity product){
        return productRepository.save(product);
    }

    @PutMapping("/update/{id}")
    public ProductEntity updateProduct(@PathVariable long id, @RequestBody ProductEntity product){
        ProductEntity updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(product.getName());
        updatedProduct.setBrand(product.getBrand());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setStock(product.getStock());
        return productRepository.save(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id){
        ProductEntity deletedProduct = productRepository.findById(id).get();
        productRepository.delete(deletedProduct);
    }

}
