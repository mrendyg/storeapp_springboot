package com.agarcia.storeapp_springboot.persistence.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDayDTO {

    private int totalSaleDay;
    private LocalDateTime dateTimeSale;

}
