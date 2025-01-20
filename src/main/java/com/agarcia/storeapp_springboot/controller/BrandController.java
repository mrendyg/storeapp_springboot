package com.agarcia.storeapp_springboot.controller;

import com.agarcia.storeapp_springboot.persistence.entity.BrandEntity;
import com.agarcia.storeapp_springboot.persistence.repository.BrandRepository;
import com.agarcia.storeapp_springboot.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<BrandEntity> getListBrand(){
        return brandService.getsListBrand();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandEntity getIdBrand(@PathVariable long id){
        return brandService.getsIdBrand(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BrandEntity createBrand(@RequestBody BrandEntity brand){
        return brandService.createsBrand(brand);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BrandEntity updateBrand(@PathVariable long id, @RequestBody BrandEntity brand){
        return brandService.updatesBrand(id, brand);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable long id){
        brandService.deletesBrand(id);
    }
}
