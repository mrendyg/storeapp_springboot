package com.agarcia.storeapp_springboot.service;

import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;


    public List<SaleEntity> getsListSale(){
        return saleRepository.findAll();
    }

    public SaleEntity getIdSale(long id){
        return saleRepository.findById(id).orElse(null);
    }

    public SaleEntity createsSale(SaleEntity sale){

        /*Check stock discount*/

        //select the list
        List<ProductEntity> products = sale.getListProduct();
        //set total = 0 as base
        int totalPrice = 0;
        //addition price of each product
        for(ProductEntity product : products){
            totalPrice += product.getPrice();
        }
        //add total price
        sale.setTotal(totalPrice);

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
