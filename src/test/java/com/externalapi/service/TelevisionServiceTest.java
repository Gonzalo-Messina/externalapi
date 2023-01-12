package com.externalapi.service;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.repository.ITelevisionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        tv.add(new Television(1002,"lg",120.55,55,20));
        tv.add(new Television(1003,"samsung",120.55,55,10));
        tv.add(new Television(1004,"sony",120.55,60,20));
        tv.add(new Television(1005,"sony",120.55,60,10));

        for (int i = 0; i < tv.size(); i++) {
            tv.get(i).setId(i+1);
        }
    }

    @Test
    void getAll() {
        when(tvRepo.findAll()).thenReturn(tv);
        Assertions.assertEquals(tv.size(),tvService.getAll().size());
        Assertions.assertEquals(tvService.getAll(),tv);
    }
    @Test
    void getAllWithEmptyList_ThrowsTelevisionException(){
        tv.clear();
        when(tvRepo.findAll()).thenReturn(tv);
        //Use lambda function
        Assertions.assertThrows(TelevisionExceptions.class,()->tvService.getAll());
    }
    @Test
    void getById() {
        Integer id = 1;
        Television tvToFind = new Television();
        for (Television tvId:tv) {
            if (tvId.getId().equals(id)){
                tvToFind = tvId;
                when(tvRepo.findById(id)).thenReturn(Optional.of(tvId));
            }
        }
        Assertions.assertEquals(Optional.of(tvToFind),tvService.getById(id));


    }

    @Test
    void getByBrand() {
        String brand = "samsung";
        //Filter list by brand
        List<Television> tvList = getFromListTelevisionByBrand(tv,brand);
        when(tvRepo.findByBrand(brand)).thenReturn(tvList);

        List<Television> tvBrand = tvService.getByBrand(brand);
        for (Television tv : tvBrand) {
            Assertions.assertEquals(brand, tv.getBrand());
        }
    }
    @Test
    void getByBrandWithEmptyList_ThrowsTelevisionException(){
        String brand = "samsung";
        tv.clear();

        when(tvRepo.findByBrand(brand)).thenReturn(tv);
        //Use lambda function
        Assertions.assertThrows(TelevisionExceptions.class,()->tvService.getByBrand(brand));
    }

    @Test
    void getByInches() {
        Integer inches = 50;
        List<Television> tvByInchesList = getFromListTelevisionByInches(tv,inches);

        when(tvRepo.findByInches(inches)).thenReturn(tvByInchesList);
        List<Television> tvInches = tvService.getByInches(inches);
        for (Television tv : tvInches) {
            Assertions.assertEquals(inches,tv.getInches());
        }
    }
    @Test
    void getByInchesWithEmptyList_ThrowsTelevisionException(){
        Integer inches = 50;
        tv.clear();

        when(tvRepo.findByInches(inches)).thenReturn(tv);
        //Use lambda function
        Assertions.assertThrows(TelevisionExceptions.class,()->tvService.getByInches(inches));
    }

    @Test
    void getTop2SalesByBrand() {
        String brand = "sony";
        List<Television> tvList = getFromListTelevisionByBrand(tv,brand);
        List<Television> tvTop2 = getTop2OfaList(tvList);

        when(tvRepo.findByBrand(brand)).thenReturn(tvList);
        List<Television> top2ByBrand = tvService.getTop2SalesByBrand(brand);

        for (int i = 0;i < 2;i++){
            Assertions.assertEquals(top2ByBrand.get(i).getCode(),tvTop2.get(i).getCode());
        }

    }

    @Test
    void getTop2SalesByInches() {
        Integer inches = 60;
        List<Television> tvList = getFromListTelevisionByInches(tv,inches);
        List<Television> tvTop2 = getTop2OfaList(tvList);

        when(tvRepo.findByInches(inches)).thenReturn(tvList);
        List<Television> top2ByBrand = tvService.getTop2SalesByInches(inches);

        for (int i = 0;i < 2;i++){
            Assertions.assertEquals(top2ByBrand.get(i).getCode(),tvTop2.get(i).getCode());
        }
    }

    @Test
    void addTelevision() {
        Television newTv = new Television(1006,"sony",120.55,80,15);

        when(tvRepo.save(newTv)).thenReturn(newTv);
        Assertions.assertEquals(newTv,tvService.addTelevision(newTv));
        //Preguntar si puedo hacer un test que evalue el tam de la lista luego de agregar,deberia tener 1 mass
    }

    //doesn't work
/*    @Test
    void updateTelevision() {
        Integer id = 1;
        Television tvUpdated = new Television(10010,"lg",150.00,50,5);
        tvUpdated.setId(id);
        Television tvToFind = tv.get(id-1);

        when(tvRepo.findById(id)).thenReturn(Optional.of(tvToFind));

        Television newTv = tvService.updateTelevision(tvToFind);

        Assertions.assertEquals(tvService.updateTelevision(tvToFind),Optional.of(tvUpdated));
    }*/

    //Ask how to test this method, because delete service return void
    @Test
    void deleteTelevision() {
        }


    /*Utils methods*/

    private List<Television> getFromListTelevisionByBrand(List<Television> tvParam, String brand){
        List<Television> tvList = new LinkedList<>();
        for (Television tv: tvParam) {
            if (tv.getBrand().equals(brand)){
                tvList.add(tv);
            }
        }
        return tvList;
    }

    private List<Television> getFromListTelevisionByInches(List<Television> tvParam, Integer inches){
        List<Television> tvList = new LinkedList<>();
        for (Television tv: tvParam) {
            if (tv.getInches().equals(inches)){
                tvList.add(tv);
            }
        }
        return tvList;
    }

    private List<Television> getTop2OfaList(List<Television> tvParam){
        List<Television> tvTop2 = new LinkedList<>();
        tvParam.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvParam.get(tvParam.size()-i));
        }
        return tvTop2;
    }

}