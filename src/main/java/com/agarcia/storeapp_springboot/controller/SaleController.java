package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.DTO.SaleDayDTO;
import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import com.agarcia.storeapp_springboot.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleService saleService;

    //Get list sale
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<SaleEntity> getListSales(){
        return saleService.getsListSale();
    }

    //get product by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SaleEntity getIdSale(@PathVariable long id){
        return saleService.getIdSale(id);
    }

    //create one sale
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SaleEntity createSale(@RequestBody SaleEntity sale){
        return saleService.createsSale(sale);
    }

    //update one sale by id
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SaleEntity updateSale(@PathVariable long id, @RequestBody SaleEntity sale){
        return saleService.updatesSale(id, sale);
    }

    //delete one sale by id
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable long id){
        saleService.deletesSale(id);
    }

    //Get the list of products of this sale
    @GetMapping("/product-detail/{id}")
    public List<ProductEntity> getListProductSale(@PathVariable long id){
        return saleService.getsDetailsProduct(id);
    }

    //total addition of sales, and amount of sales quantity
    @GetMapping("/date/{date}")
    public List<SaleDayDTO> getSaleTotalDay(){
        List<SaleEntity> saleEntityList = saleRepository.findAll();
        List<SaleDayDTO> saleDayDTOList = new ArrayList<>();
        SaleDayDTO saleDayDTO =new SaleDayDTO();

        for (SaleEntity sale: saleEntityList){
            saleDayDTO.setTotalSaleDay(sale.getDateSale().getDayOfMonth());
            saleDayDTO.setTotalSaleDay(sale.getTotal());

            saleDayDTOList.add(saleDayDTO);
            saleDayDTO = new SaleDayDTO();
        }
        return saleDayDTOList;
    }

}
