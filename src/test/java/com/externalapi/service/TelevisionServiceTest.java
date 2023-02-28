package com.externalapi.service;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.repository.ITelevisionRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.externalapi.util.ListUtil.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TelevisionServiceTest.class})
class TelevisionServiceTest implements WithAssertions {

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
        //Arrange
        when(tvRepo.findAll()).thenReturn(tv);

        //Act
        List<Television> tvTest = tvService.getAll();

        //Assert
        assertThat(tvTest)
                .hasSize(6)
                .isEqualTo(tv);
    }
    @Test
    void getAllWithEmptyList_ThrowsTelevisionException(){
        tv.clear();
        when(tvRepo.findAll()).thenReturn(tv);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getAll());
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

        assertThat(tvToFind)
                .hasFieldOrPropertyWithValue("id",1);

    }

    @Test
    void getById_withWrongId(){
        Integer id = 100;
        when(tvRepo.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getById(id));
    }

    @Test
    void getByBrand() {
        String brand = "samsung";
        //Filter list by brand
        List<Television> tvList = getFromListTelevisionByBrand(tv,brand);
        when(tvRepo.findByBrand(brand)).thenReturn(tvList);

        List<Television> tvBrand = tvService.getByBrand(brand);

        for (Television tv : tvBrand) {
            assertThat(tv)
                    .hasFieldOrPropertyWithValue("brand",brand);
        }
        assertThat(tvBrand).hasSize(tvList.size());
    }

    @Test
    void getByBrand_withWrongBrand(){
        String brand = "Non-exist";
        when(tvRepo.findByBrand(brand)).thenThrow(TelevisionExceptions.class);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getByBrand(brand));
    }
    @Test
    void getByBrandWithEmptyList_ThrowsTelevisionException(){
        String brand = "samsung";
        tv.clear();
        when(tvRepo.findByBrand(brand)).thenReturn(tv);
        //Use lambda function

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getByBrand(brand));
    }

    @Test
    void getByInches() {
        Integer inches = 50;
        List<Television> tvByInchesList = getFromListTelevisionByInches(tv,inches);
        when(tvRepo.findByInches(inches)).thenReturn(tvByInchesList);

        List<Television> tvInches = tvService.getByInches(inches);

        for (Television tv : tvInches) {
            //Assertions.assertEquals(inches,tv.getInches());
            assertThat(tv).hasFieldOrPropertyWithValue("inches",inches);
        }
        assertThat(tvInches).hasSize(tvByInchesList.size());
    }

    @Test
    void getByInches_withWrongInches(){
        Integer inches = 99;
        when(tvRepo.findByInches(inches)).thenThrow(TelevisionExceptions.class);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getByInches(inches));
    }
    @Test
    void getByInchesWithEmptyList_ThrowsTelevisionException(){
        Integer inches = 50;
        tv.clear();
        when(tvRepo.findByInches(inches)).thenReturn(tv);
        //Use lambda function
        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.getByInches(inches));
    }

    @Test
    void getTop2SalesByBrand() {
        String brand = "sony";
        List<Television> tvList = getFromListTelevisionByBrand(tv,brand);
        List<Television> tvTop2 = getTop2OfaList(tvList);
        when(tvRepo.findByBrand(brand)).thenReturn(tvList);

        List<Television> top2ByBrand = tvService.getTop2SalesByBrand(brand);

        assertThat(top2ByBrand).hasSize(2)
                .allMatch(tv->tv.getBrand().equals(brand),brand)
                .containsAll(tvTop2);//see that all objects has the correct brand

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
        assertThat(top2ByBrand).hasSize(2)
                .allMatch(tv->tv.getInches().equals(inches));//see that all objects has the correct inches
    }

    @Test
    void addTelevision() {
        Television newTv = new Television(1006,"sony",120.55,80,15);
        when(tvRepo.save(newTv)).thenReturn(newTv);

        Television tvSaved = tvService.addTelevision(newTv);

        //Assertions.assertEquals(newTv,);
        assertThat(tvSaved).isSameAs(newTv);


        //Preguntar si puedo hacer un test que evalue el tam de la lista luego de agregar,deberia tener 1 mass
    }

    @Test
    void addTelevision_withNullArgument(){
        //Television tvSaved = tvService.addTelevision(null);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.addTelevision(null));
    }

    //doesn't work
    @Test
    void updateTelevision() {
        Integer id = 1;
        Television tvUpdated = new Television(10010,"lg",150.00,50,5);
        tvUpdated.setId(id);
        when(tvRepo.existsById(id)).thenReturn(true);
        when(tvRepo.save(tvUpdated)).thenReturn(tvUpdated);

        Television newTv = tvService.updateTelevision(tvUpdated);

        assertThat(newTv).isSameAs(tvUpdated);
    }

    @Test
    void updateTelevision_withNullArgument(){
        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.updateTelevision(null));
    }

    @Test
    void updateTelevision_withWrongId(){
        Integer id = 100;
        Television tvUpdated = new Television(10010,"lg",150.00,50,5);
        tvUpdated.setId(id);
        when(tvRepo.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.updateTelevision(tvUpdated));
    }

    //Ask how to test this method, because delete service return void
/*    @Test
    void deleteTelevision() {
        Integer id = 1;
        Television newTv = new Television();
        newTv.setId(1);
        tvRepo.save(newTv);
        doNothing().when(tvRepo).deleteById(id);

        tvService.deleteTelevision(id);

        verify(tvRepo).deleteById(id);
    }*/

    @Test
    void deleteTelevision_withNullId(){
        //when(tvService.deleteTelevision(null)).thenThrow(TelevisionExceptions.class);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.deleteTelevision(null));
    }

    @Test
    void deleteTelevision_withBadId(){
        Integer id = 100;
        //when(tvService.deleteTelevision(null)).thenThrow(TelevisionExceptions.class);

        assertThatExceptionOfType(TelevisionExceptions.class)
                .isThrownBy(()->tvService.deleteTelevision(id));
    }

}