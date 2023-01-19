package com.externalapi.util;

import com.externalapi.entity.Television;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListUtil {
    public static List<Television> getFromListTelevisionByBrand(List<Television> tvParam, String brand){
        List<Television> tvList = new LinkedList<>();
        for (Television tv: tvParam) {
            if (tv.getBrand().equals(brand)){
                tvList.add(tv);
            }
        }
        return tvList;
    }

    public static List<Television> getFromListTelevisionByInches(List<Television> tvParam, Integer inches){
        List<Television> tvList = new LinkedList<>();
        for (Television tv: tvParam) {
            if (tv.getInches().equals(inches)){
                tvList.add(tv);
            }
        }
        return tvList;
    }

    public static List<Television> getTop2OfaList(List<Television> tvParam){
        List<Television> tvTop2 = new LinkedList<>();
        tvParam.sort(Comparator.comparing(Television::getSales)
                .thenComparing(Television::getSales));

        for (int i = 1; i <= 2; i++) {
            tvTop2.add(tvParam.get(tvParam.size()-i));
        }
        return tvTop2;
    }
}
