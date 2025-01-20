package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import com.agarcia.storeapp_springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //view list of products
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductEntity> getListProduct(){
        return productService.getsListProduct();
    }

    //view products by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductEntity getIdProduct(@PathVariable long id){
        return productService.getsIdProduct(id);
    }

    //Create new product
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductEntity createProduct(@RequestBody ProductEntity product){
        return productService.createsProduct(product);
    }

    //update product
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductEntity updateProduct(@PathVariable long id, @RequestBody ProductEntity product){
        return productService.updatesProduct(id, product);
    }

    //delete product
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id){
        productService.deletesProduct(id);
    }

    //GetProduct low stock
    @GetMapping("/lowstock")
    public List<ProductEntity> getLowStockProduct(){
        int lowStock = 5;

        List<ProductEntity> listProduct = this.getListProduct();
        List<ProductEntity> listLowStock = new ArrayList<ProductEntity>();

        for(ProductEntity product: listProduct){
            if(product.getStock() <= lowStock){
                listLowStock.add(product);
            }
        }
        return listLowStock;
    }

}
