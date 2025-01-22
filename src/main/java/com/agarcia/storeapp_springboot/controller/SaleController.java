package com.agarcia.storeapp_springboot.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // Obtener todas las ventas
        List<SaleEntity> allSales = saleRepository.findAll();

        // Filtrar las ventas por la fecha proporcionada
        List<SaleEntity> daySaleList = allSales.stream()
                .filter(sale -> daySale.equals(sale.getDaySale()))
                .collect(Collectors.toList());

        // Calcular el total vendido
        int totalSold = daySaleList.stream()
                .flatMap(sale -> sale.getListProduct().stream()) // Suponiendo que SaleEntity tiene una lista de ProductEntity
                .mapToInt(ProductEntity::getPrice)
                .sum();

        int totalSales = daySaleList.size();

        // Crear la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("sales", daySaleList); // Lista de ventas para el día
        response.put("totalSold", totalSold); // Total vendido para el día
        response.put("totalSales", totalSales); //Total de ventas
        return ResponseEntity.ok(response);
    }



}
