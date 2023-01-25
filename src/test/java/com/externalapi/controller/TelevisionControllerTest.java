package com.externalapi.controller;

import com.externalapi.entity.Television;
import com.externalapi.service.TelevisionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URL;
import java.util.*;

import static com.externalapi.util.ListUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TelevisionController.class)
class TelevisionControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private TelevisionService tvService;

    private List<Television> tv;
    private URL baseUrl;

    @BeforeEach
    void setUp() throws Exception{
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

        this.baseUrl = new URL("http://localhost:8090/televisions");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() throws Exception{
       when(tvService.getAll()).thenReturn(tv);

        //generate get request
        mockMVC.perform(get("/televisions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())//indicates that the status expected
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//indicates that the response is a json
                .andExpect(jsonPath("$.data[0].code").value(1000));//test the code of the first item

        verify(tvService).getAll();//verify that when tvService is use, it calls getAll service

    }

/*    @Test
    void getAll_withEmptyList_return404() throws Exception{
        tv.clear();
        when(tvService.getAll()).thenReturn(tv);

        mockMVC.perform(get("/televisions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("No object retrieve"));
    }*/

    @Test
    void getTelevisionById() throws Exception{
        Integer id = 1;
        Television tvById = tv.get(id-1);
        Mockito.when(tvService.getById(id)).thenReturn(Optional.of(tvById));

        mockMVC.perform(get(baseUrl+"/id?id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1));

        verify(tvService).getById(id);//verify that when tvService is use, it calls getAll service


    }

    @Test
    void getTelevisionsByBrand() throws Exception {
        String brand = "samsung";
        List<Television> tvBrand = getFromListTelevisionByBrand(tv,brand);
        when(tvService.getByBrand(brand)).thenReturn(tvBrand);

        mockMVC.perform(get(baseUrl+"/brand?brand="+brand))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].brand").value(brand))
                .andExpect(jsonPath("$.data.length()").value(tvBrand.size()));

    }

    @Test
    void getTelevisionsByBrand_argumentIsNumeric_then400() throws Exception {
        String brand = "15";
        List<Television> tvBrand = getFromListTelevisionByBrand(tv,brand);
        when(tvService.getByBrand(brand)).thenReturn(tvBrand);

        mockMVC.perform(get(baseUrl+"/brand?brand="+brand))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.message")
                        .value("ERROR: The string: "+brand+" is not a valid string"));
    }

    @Test
    void getTop2SalesByBrand() throws Exception {
        String brand = "samsung";
        List<Television> tvBrand = getFromListTelevisionByBrand(tv,brand);
        List<Television> top2ByBrand = getTop2OfaList(tvBrand);
        when(tvService.getTop2SalesByBrand(brand)).thenReturn(top2ByBrand);

        mockMVC.perform(get(baseUrl+"/top2salesByBrand?brand="+brand))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].brand").value(brand))
                .andExpect(jsonPath("$.data[1].brand").value(brand))
                .andExpect(jsonPath("$.data.length()").value(top2ByBrand.size()));
    }

    @Test
    void getTop2SalesByBrand_argumentIsNumeric_then400() throws Exception {
        String brand = "15";
        List<Television> tvBrand = getFromListTelevisionByBrand(tv,brand);
        when(tvService.getByBrand(brand)).thenReturn(tvBrand);

        mockMVC.perform(get(baseUrl+"/top2salesByBrand?brand="+brand))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.message")
                        .value("ERROR: The string: "+brand+" is not a valid string"));
    }

    @Test
    void getTop2SalesByInches() throws Exception {
        Integer inches = 50;
        List<Television> tvInches = getFromListTelevisionByInches(tv,inches);
        List<Television> top2ByInches = getTop2OfaList(tvInches);
        when(tvService.getTop2SalesByInches(inches)).thenReturn(top2ByInches);

        mockMVC.perform(get(baseUrl+"/top2salesByInches?inches="+inches))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].inches").value(inches))
                .andExpect(jsonPath("$.data[1].inches").value(inches))
                .andExpect(jsonPath("$.data.length()").value(top2ByInches.size()));
    }

    @Test
    void getTelevisionsByInches() throws Exception {
        Integer inches = 50;
        List<Television> tvInches = getFromListTelevisionByInches(tv,inches);
        when(tvService.getByInches(inches)).thenReturn(tvInches);

        mockMVC.perform(get(baseUrl+"/inches?inches="+inches))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].inches").value(inches))
                .andExpect(jsonPath("$.data.length()").value(tvInches.size()));
    }

    @Test
    void addTelevision() throws Exception {
        Television newTv = new Television(1006,"sony",120.55,80,15);
        ObjectMapper mapper = new ObjectMapper();
        when(tvService.addTelevision(newTv)).thenReturn(newTv);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "Successfully");
        map.put("status", 200);
        map.put("data", newTv);

        mockMVC.perform(MockMvcRequestBuilders.post("/televisions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(map)))//used mapper to convert object
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateTelevision() throws Exception {
        Television newTv = new Television(1006,"sony",120.55,80,15);
        ObjectMapper mapper = new ObjectMapper();
        when(tvService.updateTelevision(newTv)).thenReturn(newTv);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "Successfully");
        map.put("status", 200);
        map.put("data", newTv);

        mockMVC.perform(MockMvcRequestBuilders.put("/televisions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(map)))//used mapper to convert object
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void deleteTelevision() throws Exception {
        Integer id = 1;
        when(tvService.getById(id)).thenReturn(Optional.of(tv.get(0)));
        //when(tvService.deleteTelevision(id)).thenReturn(void);

        mockMVC.perform(delete(baseUrl+"/id?id="+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}