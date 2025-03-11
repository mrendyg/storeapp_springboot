package com.agarcia.storeapp_springboot.service;

import com.agarcia.storeapp_springboot.persistence.DTO.HighestSaleDTO;
import com.agarcia.storeapp_springboot.persistence.entity.ProductEntity;
import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import com.agarcia.storeapp_springboot.persistence.repository.ProductRepository;
import com.agarcia.storeapp_springboot.persistence.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    //List of sales
    public List<SaleEntity> getsListSale(){
        return saleRepository.findAll();
    }

    // Get Sale by id
    public SaleEntity getIdSale(long id){
        return saleRepository.findById(id).orElse(null);
    }

    //Update sale by id
    public SaleEntity updatesSale(long id, SaleEntity sale){
        SaleEntity updatedSale = saleRepository.findById(id).get();
        updatedSale.setClient(sale.getClient());
        updatedSale.setTotal(sale.getTotal());
        updatedSale.setListProduct(sale.getListProduct());
        return saleRepository.save(updatedSale);
    }

    //Delete sale by id
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

    //Create one sale
    public SaleEntity createsSale(SaleEntity sale){
        //select the list
        // Product list of the products select
        List<ProductEntity> products = new ArrayList<>();
        List<ProductEntity> updateProduct = new ArrayList<>();

        // Get products from the database using only the product ID
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

        for (ProductEntity product: products){
            if(product.getStock() <= 0){
                throw  new ResponseStatusException(HttpStatus.CONFLICT, "The product" + product.getName() +
                        " is out stock");
            }
            else {
                int removeProduct = product.getStock() - 1;
                product.setStock(removeProduct);
                productRepository.save(product);
            }
        }
        //add total price
        sale.setTotal(totalPrice);
        //add hours and date of creation time
        sale.setDateSale(LocalDateTime.now());
        sale.setDaySale(LocalDate.now());

        return saleRepository.save(sale);
    }

    // Total Sale by day
    public ResponseEntity<Map<String, Object>> getTotalSaleDay(LocalDate daySale){
        // Get all sales
        List<SaleEntity> allSales = saleRepository.findAll();
        // Filter sales by the date provided
        List<SaleEntity> daySaleList = allSales.stream()
                .filter(sale -> daySale.equals(sale.getDaySale()))
                .toList();

        // Calculate the total sold
        int totalSold = daySaleList.stream()
                .flatMap(sale -> sale.getListProduct().stream())
                .mapToInt(ProductEntity::getPrice)
                .sum();

        int totalSales = daySaleList.size();
        // Create request
        Map<String, Object> response = new HashMap<>();
        response.put("totalSold", totalSold); // Total sold for the day
        response.put("totalSales", totalSales); //Total of sales
        return ResponseEntity.ok(response);
    }

    //Highest Sale
    public HighestSaleDTO getsHighestSaleDTO(){
        SaleEntity sale = saleRepository.findTopByOrderByTotalDesc();

        if (sale == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sales found");
        }
        else {
            HighestSaleDTO highestSaleDTO = new HighestSaleDTO();
            highestSaleDTO.setIdSale(sale.getId());
            highestSaleDTO.setTotalSale(sale.getTotal());
            highestSaleDTO.setQuantityProducts(sale.getListProduct().size());
            highestSaleDTO.setNameClient(sale.getClient().getName());
            highestSaleDTO.setLastNameClient(sale.getClient().getLastName());

            return highestSaleDTO;
        }
    }

    //

}
