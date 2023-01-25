package com.externalapi.service;

import com.externalapi.entity.Television;
import com.externalapi.exceptions.TelevisionExceptions;
import com.externalapi.repository.ITelevisionRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.externalapi.util.ListUtil.getTop2OfaList;


@Service
public class TelevisionService {

    @Autowired
    private ITelevisionRepository tvRepo;
    private static final Log logger = LogFactory.getLog(TelevisionService.class);
    private static final String NO_TV_ID = "No television found for id:";

    @Transactional(readOnly = true)
    public List<Television> getAll(){
        List<Television> tv = tvRepo.findAll();
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
            logger.error(NO_TV_ID+id);
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

        return getTop2OfaList(tvBrand);

    }

    @Transactional(readOnly = true)
    public List<Television> getTop2SalesByInches(Integer inches){
        List<Television> tvInches = getByInches(inches);

        return getTop2OfaList(tvInches);

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television addTelevision(Television tvParm){
        if (Objects.isNull(tvParm)){
            logger.error("Object to add is null");
            throw new TelevisionExceptions("The Object to add is null",HttpStatus.NO_CONTENT);
        }
        return tvRepo.save(tvParm);

    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public Television updateTelevision(Television tvParm){
        if (Objects.isNull(tvParm)){
            logger.error("Object to update is null");
            throw new TelevisionExceptions("The Object to update is null",HttpStatus.NO_CONTENT);
        }
        if(!tvRepo.existsById(tvParm.getId())){
            logger.error(NO_TV_ID+tvParm.getId());
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }
        Optional<Television> tvRetrieved = tvRepo.findById(tvParm.getId());
        if (tvRetrieved.isPresent() &&tvRetrieved.get().getVersion() != tvParm.getVersion()){
            throw new OptimisticLockingFailureException("The television to update with id"+
                    tvParm.getId()+"has been modified since it was retrieved");
        }

        return tvRepo.save(tvParm);
    }

    @Transactional(rollbackFor = {TelevisionExceptions.class})
    public void deleteTelevision(Integer id){
        if (Objects.isNull(id)){
            logger.error("The id is null");
            throw new TelevisionExceptions("The id to delete is null",HttpStatus.NO_CONTENT);
        }
        if(!tvRepo.existsById(id)){
            logger.error(NO_TV_ID+id);
            throw new TelevisionExceptions("The television don't exist",HttpStatus.NO_CONTENT);
        }
        tvRepo.deleteById(id);

    }
}
