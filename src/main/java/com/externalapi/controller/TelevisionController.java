package com.externalapi.controller;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.response.ResponseHandler;
import com.externalapi.service.TelevisionService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    private static final Log logger = LogFactory.getLog(TelevisionService.class);

    @Autowired
    private TelevisionService tvService;

    @GetMapping()
    //Puedo agregar ejemplos en swagger con etiquetas aca
    public List<Television> getAll(){
        return tvService.getAll();
    }

    @GetMapping("/id")
    public Optional<Television> getTelevisionById(@RequestParam(value="id") Integer id){
        return tvService.getById(id);
    }

    @GetMapping("/brand")
    public List<Television> getTelevisionsByBrand(@RequestParam(value = "brand") String brand){
        if (NumberUtils.isCreatable(brand)) {//this method retrieves true if the string is numeric
            logger.error("ERROR: The string: "+brand+" is not a valid string");
            throw new TelevisionExceptions("ERROR: The string: "+brand+" is not a valid string", HttpStatus.BAD_REQUEST);
        }
        return tvService.getByBrand(brand);
    }

    @GetMapping("/top2salesByBrand")
    public List<Television> getTop2SalesByBrand(@RequestParam (value = "brand") String brand){
        if (NumberUtils.isCreatable(brand)) {//this method retrieves true if the string is numeric
            logger.error("ERROR: The string: "+brand+" is not a valid string");
            throw new TelevisionExceptions("ERROR: The string: "+brand+" is not a valid string", HttpStatus.BAD_REQUEST);
        }
        return tvService.getTop2SalesByBrand(brand);
    }

    @GetMapping("/top2salesByInches")
    public List<Television> getTop2SalesByInches(@RequestParam (value = "inches") Integer inches){
        return tvService.getTop2SalesByInches(inches);
    }
    @GetMapping("/inches")
    public List<Television> getTelevisionsByInches(@RequestParam(value = "inches") Integer inches){
        return tvService.getByInches(inches);
    }

    @PostMapping()
    public Television addTelevision(@RequestBody Television tvParm) {
        return tvService.addTelevision(tvParm);
    }

    @PutMapping()
    public Television updateTelevision (@RequestBody Television tvParm) {
        return tvService.updateTelevision(tvParm);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Object> deleteTelevision(@RequestParam(value = "id") Integer id) {
        Optional<Television> tv  = tvService.getById(id);
        tvService.deleteTelevision(id);
        return ResponseHandler.generateResponse("Successfully deleted",HttpStatus.OK,tv);

    }
}