package com.externalapi.repository;

import com.externalapi.entity.Television;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITelevisionRepository extends JpaRepository<Television,Integer> {
    List<Television> findByBrand(String brand);

    List<Television> findByInches(Integer inches);

}
