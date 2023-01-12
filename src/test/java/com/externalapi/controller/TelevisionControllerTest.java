package com.externalapi.controller;

import com.externalapi.entity.Television;
import com.externalapi.service.TelevisionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.web.servlet.function.ServerResponse.status;

@WebMvcTest(TelevisionController.class)
class TelevisionControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private TelevisionService tvService;

    private List<Television> tv;

    @BeforeEach
    void setUp() {
        tv = new LinkedList<>();
        tv.add(new Television(1000,"lg",120.55,50,20));
        tv.add(new Television(1001,"samsung",120.55,50,10));
        tv.add(new Television(1002,"lg",120.55,55,20));
        tv.add(new Television(1003,"samsung",120.55,55,10));
        tv.add(new Television(1004,"sony",120.55,60,20));
        tv.add(new Television(1005,"sony",120.55,60,10));

        for (int i = 0; i < tv.size(); i++) {
            tv.get(i).setId(i+1);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() throws Exception{
        Mockito.when(tvService.getAll()).thenReturn(tv);

        //generate get request
        mockMVC.perform(get("/televisions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())//indicates that the status expected
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//indicates that the response is a json
                .andExpect(jsonPath("$.data[:1].code").value(1000));//test the code of the first item

        verify(tvService).getAll();//verify that when tvService is use, it calls getAll service

    }

    @Test
    void getTelevisionById() {
    }

    @Test
    void getTelevisionsByBrand() {
    }

    @Test
    void getTop2SalesByBrand() {
    }

    @Test
    void getTop2SalesByInches() {
    }

    @Test
    void getTelevisionsByInches() {
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