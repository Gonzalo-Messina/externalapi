package com.externalapi.dto;

import com.externalapi.entity.Television;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class TelevisionDTO implements Serializable {

    private String brand;
    private Integer inches;
    private Integer sales;

    public TelevisionDTO(Television tv){
        this.brand = tv.getBrand();
        this.inches = tv.getInches();
        this.sales = tv.getSales();
    }


}
