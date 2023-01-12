package com.externalapi.service;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.repository.ITelevisionRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TelevisionService {

    @Autowired
    private ITelevisionRepository tvRepo;
    private static final Log logger = LogFactory.getLog(TelevisionService.class);

    @Transactional(readOnly = true)
    public List<Television> getAll(){
        logger.info("Entering getAll service");
        List<Television> tv = (List<Television>) tvRepo.findAll();
        if (tv.isEmpty()) {
            logger.error("No object retrieve");
            logger.error("Exit getAll service with error");
            throw new TelevisionExceptions("no object retrieve", HttpStatus.NO_CONTENT);
        }
        logger.info("Exit getAll service ok");
        return tv;
    }

    @Transactional(readOnly = true)
    public Optional<Television> getById(Integer id){
        logger.info("Entering getById method");
        Optional<Television> tv = tvRepo.findById(id);
        if (tv.isEmpty()){
            logger.error("No television found for id:"+id);
            logger.error("Exit getById method with error");
            throw new TelevisionExceptions("No television for id:"+id,HttpStatus.NO_CONTENT);
        }
        logger.info("Exit getById service ok");
        return tv;

    }

    @Transactional(readOnly = true)
    public List<Television> getByBrand(String brand){
        logger.info("Entering getByBrand service");
        List<Television> tv = tvRepo.findByBrand(brand);
        if (tv.isEmpty()){
            logger.error("No television found for brand:"+brand);
            logger.error("Exit getByBrand method with error");
            throw new TelevisionExceptions("No television for brand:"+brand,HttpStatus.NO_CONTENT);
        }
        return tv;
    }

    @Transactional(readOnly = true)
    public List<Television> getByInches(Integer inches){
        logger.info("Entering getByInches service");
        List<Television> tv = tvRepo.findByInches(inches);
        if (tv.isEmpty()){
            logger.error("No television found for inches:"+inches);
            logger.error("Exit getByInches method with error");
            throw new TelevisionExceptions("No television for inches:"+inches,HttpStatus.NO_CONTENT);
        }
        return tv;
    }

    @Transactional(readOnly = true)
    public List<Television> getTop2SalesByBrand(String brand){
        logger.info("Entering getTop2SalesByBrand service");
        List<Television> tvBrand = getByBrand(brand);
        List<Television> tvTop2 = new LinkedList<>();

        tvBrand.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvBrand.get(tvBrand.size()-i));
        }
        logger.info("Exit getTop2SalesByBrand with OK");
        return tvTop2;

    }

    @Transactional(readOnly = true)
    public List<Television> getTop2SalesByInches(Integer inches){
        logger.info("Entering getTop2SalesByBrand service");
        List<Television> tvInches = getByInches(inches);
        List<Television> tvTop2 = new LinkedList<>();

        tvInches.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvInches.get(tvInches.size()-i));
        }
        logger.info("Exit getTop2SalesByInches with OK");
        return tvTop2;

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television addTelevision(Television tvParm){
        logger.info("Entering addTelevision service");
        if (Objects.isNull(tvParm)){
            logger.error("Object to add is null");
            logger.error("Rollback transaction");
            logger.error("Exit addTelevision service with error");
            throw new TelevisionExceptions("The Object to add is null",HttpStatus.NO_CONTENT);
        }
        Television tv = tvRepo.save(tvParm);
        logger.info("Television added successfully");
        return tv;

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television updateTelevision(Television tvParm){
        logger.info("Entering updateTelevision Service");
        if (Objects.isNull(tvParm)){
            logger.error("Object to update is null");
            logger.info("Rollback transaction");
            logger.error("Exit updateTelevision service with error");
            throw new TelevisionExceptions("The Object to update is null",HttpStatus.NO_CONTENT);
        }
        Optional<Television> tv = tvRepo.findById(tvParm.getId());
        if (Objects.isNull(tv)){
            logger.error("No television found for id:"+tvParm.getId());
            logger.error("Exit updateTelevision service with error");
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }
        logger.info("Exit updateTelevision service with ok");
        return tvRepo.save(tvParm);
    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public void deleteTelevision(Integer id){
        logger.info("Entering deleteTelevision service");
        if (Objects.isNull(id)){
            logger.error("The id is null");
            logger.error("Rollback transaction");
            throw new TelevisionExceptions("The id to delete is null",HttpStatus.NO_CONTENT);
        }
        Optional<Television> tv = tvRepo.findById(id);
        if (tv.isEmpty()){
            logger.error("No television found for id:"+id);
            logger.error("Rollback transaction");
            throw new TelevisionExceptions("No television found for id",HttpStatus.NO_CONTENT);
        }
        tvRepo.deleteById(id);

    }
}
