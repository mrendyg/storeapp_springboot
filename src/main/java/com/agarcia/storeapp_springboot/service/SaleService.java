package com.agarcia.storeapp_springboot.service;

import com.agarcia.storeapp_springboot.persistence.entity.ClientEntity;
import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ClientRepository;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;


    public List<SaleEntity> getsListSale(){
        return saleRepository.findAll();
    }

    public SaleEntity getIdSale(long id){
        return saleRepository.findById(id).orElse(null);
    }

    public SaleEntity createsSale(SaleEntity sale){

        // List of products selected from the IDs provided
        List<ProductEntity> products = new ArrayList<>();

        //Get the product from the db using the product id
        for (ProductEntity product : sale.getListProduct()) {
            ProductEntity productFromDb = productRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            products.add(productFromDb);
        }


        //set total = 0 as base
        int totalPrice = 0;
        //addition price of each product
        for(ProductEntity product : products){
            totalPrice += product.getPrice();
        }
        //add total price
        sale.setTotal(totalPrice);

        //remove stock of product that are on the list in -1
        List<ProductEntity> updateProduct = new ArrayList<>();
        for (ProductEntity product: products){
            int removeProduct = product.getStock() - 1;
            product.setStock(removeProduct);
            productRepository.save(product);
        }

        //add hours and date of creation time
        sale.setDateSale(LocalDateTime.now());

        return saleRepository.save(sale);
    }

    public SaleEntity updatesSale(long id, SaleEntity sale){
        SaleEntity updatedSale = saleRepository.findById(id).get();
        updatedSale.setClient(sale.getClient());
        updatedSale.setTotal(sale.getTotal());
        updatedSale.setListProduct(sale.getListProduct());
        return saleRepository.save(updatedSale);
    }

    public void deletesSale(long id){
        SaleEntity deletedSale = saleRepository.findById(id).get();
        saleRepository.delete(deletedSale);
    }

    //Get the list of products of this sale
    public List<ProductEntity> getsDetailsProduct(long id){
        SaleEntity sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            return sale.getListProduct();
        }
        return null;
    }
}
