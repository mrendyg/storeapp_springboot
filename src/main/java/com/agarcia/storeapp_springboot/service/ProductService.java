package com.agarcia.storeapp_springboot.service;

import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getsListProduct(){
        return productRepository.findAll();
    }

    public ProductEntity getsIdProduct(long id){
        return productRepository.findById(id).orElse(null);
    }

    public ProductEntity createsProduct(ProductEntity product){
        return productRepository.save(product);
    }

    public ProductEntity updatesProduct(long id, ProductEntity product){
        ProductEntity updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setStock(product.getStock());
        return productRepository.save(updatedProduct);
    }

    public void deletesProduct(long id){
        ProductEntity deletedProduct = productRepository.findById(id).get();
        productRepository.delete(deletedProduct);
    }
}
