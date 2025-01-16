package com.agarcia.storeapp_springboot.persistence.repository;

import com.agarcia.storeapp_springboot.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
