package com.externalapi.service;

import com.externalapi.entity.Television;
import com.externalapi.repository.ITelevisionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TelevisionServiceTest.class})
class TelevisionServiceTest {

    @Mock
    ITelevisionRepository tvRepo;

     @InjectMocks
     TelevisionService tvService;

    public List<Television> tv;

    @BeforeEach
    void initDB(){
        tv = new LinkedList<>();
        tv.add(new Television(1000,"lg",120.55,50,20));
        tv.add(new Television(1001,"samsung",120.55,50,10));
    }

    @Test
    void getAll() {
        when(tvRepo.findAll()).thenReturn(tv);
        Assertions.assertEquals(2,tvService.getAll().size());
    }
    @Test
    void getById() {

    }

    @Test
    void getByBrand() {
        String brand = "samsung";
        when(tvRepo.findByBrand(brand)).thenReturn(tv);

        List<Television> tvBrand = tvService.getByBrand(brand);
        for (Television tv : tvBrand) {
            Assertions.assertEquals(brand, tv.getBrand());
        }
    }

    @Test
    void getByInches() {
    }

    @Test
    void getTop2SalesByBrand() {
    }

    @Test
    void getTop2SalesByInches() {
    }

    @Test
    void addTelevision() {
    }

    @Test
    void updateTelevision() {
    }

    @Test
    void deleteTelevision() {
    }

}