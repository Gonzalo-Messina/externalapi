package com.externalapi.controller;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.reponse.ResponseHandler;
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
    public ResponseEntity<Object> getAll(){
        logger.info("Entering getAll endpoint");
        List<Television> tv = tvService.getAll();
        logger.info("Exit getAll endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getTelevisionById(@RequestParam(value="id") Integer id){
        logger.info("Entering getTelevisionById endpoint");
        Optional<Television> tv  = tvService.getById(id);
        logger.info("Exit getTelevisionById endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @GetMapping("/brand")
    public ResponseEntity<Object> getTelevisionsByBrand(@RequestParam(value = "brand") String brand){
        logger.info("Entering getTelevisionByBrand endpoint");
        if (NumberUtils.isCreatable(brand)) {//this method retrieves true if the string is numeric
            logger.error("ERROR: The string: "+brand+" is not a valid string");
            logger.error("Exit getTelevisionByBrand endpoint with error");
            throw new TelevisionExceptions("ERROR: The string: "+brand+" is not a valid string", HttpStatus.BAD_REQUEST);
        }
            List<Television> tv = tvService.getByBrand(brand);
        logger.info("Exit getTelevisionByBrand endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @GetMapping("/top2salesByBrand")
    public ResponseEntity<Object> getTop2SalesByBrand(@RequestParam (value = "brand") String brand){
        logger.info("Entering getTop2SalesByBrand endpoint");
        if (NumberUtils.isCreatable(brand)) {//this method retrieves true if the string is numeric
            logger.error("ERROR: The string: "+brand+" is not a valid string");
            logger.error("Exit getTop2SalesByBrand endpoint with error");
            throw new TelevisionExceptions("ERROR: The string: "+brand+" is not a valid string", HttpStatus.BAD_REQUEST);
        }
        List<Television> tv = tvService.getTop2SalesByBrand(brand);
        logger.info("Exit getTop2SalesByBrand endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @GetMapping("/top2salesByInches")
    public ResponseEntity<Object> getTop2SalesByInches(@RequestParam (value = "inches") Integer inches){
        logger.info("Entering getTop2SalesByInches endpoint");
        List<Television> tv = tvService.getTop2SalesByInches(inches);
        logger.info("Exit getTop2SalesByBrand endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }
    @GetMapping("/inches")
    public ResponseEntity<Object> getTelevisionsByInches(@RequestParam(value = "inches") Integer inches){
        logger.info("Entering getTelevisionByInches endpoint");
        List<Television> tv = tvService.getByInches(inches);
        logger.info("Exit getTelevisionByInches endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @PostMapping()
    public ResponseEntity<Object> addTelevision(@RequestBody Television tvParm) {
        logger.info("Entering addTelevision endpoint");
        Television tv = tvService.addTelevision(tvParm);
        logger.info("Exit addTelevision endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @PutMapping()
    public ResponseEntity<Object> updateTelevision (@RequestBody Television tvParm) {
        Television tv = tvService.updateTelevision(tvParm);
        return ResponseHandler.generateResponse("Successfully",HttpStatus.OK,tv);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Object> deleteTelevision(@RequestParam(value = "id") Integer id) {
        logger.info("Entering deleteTelevision endpoint");
        Optional<Television> tv  = tvService.getById(id);
        tvService.deleteTelevision(id);
        logger.info("Exit deleteTelevision endpoint with status: OK");
        return ResponseHandler.generateResponse("Successfully deleted",HttpStatus.OK,tv);

    }
}