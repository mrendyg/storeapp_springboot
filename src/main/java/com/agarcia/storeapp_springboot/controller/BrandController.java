package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.BrandEntity;
import com.agarcia.storeapp_springboot.persistence.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/list")
    public List<BrandEntity> getListBrand(){
        return brandRepository.findAll();
    }

    @GetMapping("/{id}")
    public BrandEntity getIdBrand(@PathVariable long id){
        return brandRepository.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public BrandEntity createBrand(@RequestBody BrandEntity brand){
        return brandRepository.save(brand);
    }




}
