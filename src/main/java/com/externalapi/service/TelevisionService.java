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
        List<Television> tv = (List<Television>) tvRepo.findAll();
        if (tv.isEmpty()) {
            logger.error("No object retrieve");
            throw new TelevisionExceptions("No object retrieve", HttpStatus.NOT_FOUND);
        }
        return tv;
    }

    @Transactional(readOnly = true)
    public Optional<Television> getById(Integer id){
        Optional<Television> tv = tvRepo.findById(id);
        if (tv.isEmpty()){
            logger.error("No television found for id:"+id);
            throw new TelevisionExceptions("No television for id:"+id,HttpStatus.NO_CONTENT);
        }
        return tv;

    }

    @Transactional(readOnly = true)
    public List<Television> getByBrand(String brand){
        List<Television> tv = tvRepo.findByBrand(brand);
        if (tv.isEmpty()){
            logger.error("No television found for brand:"+brand);
            throw new TelevisionExceptions("No television for brand:"+brand,HttpStatus.NO_CONTENT);
        }
        return tv;
    }

    @Transactional(readOnly = true)
    public List<Television> getByInches(Integer inches){
        List<Television> tv = tvRepo.findByInches(inches);
        if (tv.isEmpty()){
            logger.error("No television found for inches:"+inches);
            throw new TelevisionExceptions("No television for inches:"+inches,HttpStatus.NO_CONTENT);
        }
        return tv;
    }

    @Transactional(readOnly = true)
    public List<Television> getTop2SalesByBrand(String brand){
        List<Television> tvBrand = getByBrand(brand);
        List<Television> tvTop2 = new LinkedList<>();

        tvBrand.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvBrand.get(tvBrand.size()-i));
        }
        return tvTop2;

    }

    @Transactional(readOnly = true)
    public List<Television> getTop2SalesByInches(Integer inches){
        List<Television> tvInches = getByInches(inches);
        List<Television> tvTop2 = new LinkedList<>();

        tvInches.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvInches.get(tvInches.size()-i));
        }
        return tvTop2;

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television addTelevision(Television tvParm){
        if (Objects.isNull(tvParm)){
            logger.error("Object to add is null");
            throw new TelevisionExceptions("The Object to add is null",HttpStatus.NO_CONTENT);
        }
        Television tv = tvRepo.save(tvParm);
        return tv;

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television updateTelevision(Television tvParm){
        if (Objects.isNull(tvParm)){
            logger.error("Object to update is null");
            throw new TelevisionExceptions("The Object to update is null",HttpStatus.NO_CONTENT);
        }
        //Optional<Television> tv = tvRepo.findById(tvParm.getId());
        if(!tvRepo.existsById(tvParm.getId())){
            logger.error("No television found for id:"+tvParm.getId());
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }
/*        if (Objects.isNull(tv)){
            logger.error("No television found for id:"+tvParm.getId());
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }*/
        return tvRepo.save(tvParm);
    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public void deleteTelevision(Integer id){
        if (Objects.isNull(id)){
            logger.error("The id is null");
            throw new TelevisionExceptions("The id to delete is null",HttpStatus.NO_CONTENT);
        }
        //Optional<Television> tv = tvRepo.findById(id);
        if(!tvRepo.existsById(id)){
            logger.error("No television found for id:"+id);
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }
        /*if (tv.isEmpty()){
            logger.error("No television found for id:"+id);
            throw new TelevisionExceptions("No television found for id",HttpStatus.NO_CONTENT);
        }*/
        tvRepo.deleteById(id);

    }
}
