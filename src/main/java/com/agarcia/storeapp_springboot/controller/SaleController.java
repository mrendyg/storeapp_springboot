package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/list")
    public List<SaleEntity> getListSales(){
        return saleRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public SaleEntity getIdSale(@PathVariable long id){
        return saleRepository.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public SaleEntity createSale(@RequestBody SaleEntity sale){
        sale.setDateSale(LocalDateTime.now());
        return saleRepository.save(sale);
    }

    @PutMapping("/update/{id}")
    public SaleEntity updateSale(@PathVariable long id, @RequestBody SaleEntity sale){
        SaleEntity updatedSale = saleRepository.findById(id).get();
        updatedSale.setClient(sale.getClient());
        updatedSale.setTotal(sale.getTotal());
        updatedSale.setListProduct(sale.getListProduct());
        return saleRepository.save(updatedSale);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSale(@PathVariable long id){
        SaleEntity deletedSale = saleRepository.findById(id).get();
        saleRepository.delete(deletedSale);
    }


}
