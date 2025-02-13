package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.DTO.HighestSaleDTO;
import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import com.agarcia.storeapp_springboot.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/date/{daySale}")
    public ResponseEntity<Map<String, Object>> getDaySaleList(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate daySale) {
        return saleService.getTotalSaleDay(daySale);
    }


    //Get id sale, total sale, quantity products, name client, lastname client of the highest sale
    @GetMapping("/highest-sale")
    public HighestSaleDTO getHighestSale(){
        SaleEntity sale = saleRepository.findTopByOrderByTotalDesc();

        if (sale == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron ventas.");
        }

        HighestSaleDTO highestSaleDTO = new HighestSaleDTO();
        highestSaleDTO.setIdSale(sale.getId());
        highestSaleDTO.setTotalSale(sale.getTotal());
        highestSaleDTO.setQuantityProducts(sale.getListProduct().size()); // Cantidad de productos
        highestSaleDTO.setNameClient(sale.getClient().getName()); // Nombre del cliente
        highestSaleDTO.setLastNameClient(sale.getClient().getLastName());

        return highestSaleDTO;

    }

}
