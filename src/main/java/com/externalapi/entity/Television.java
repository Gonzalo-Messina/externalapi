package com.externalapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Comparator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "television")
public class Television implements Serializable, Comparator<Television> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    @NonNull
    private Integer code;

    @Column(name = "brand")
    @NonNull
    private String brand;

    @Column(name = "price")
    @NonNull
    private double price;

    @Column(name = "inches")
    @NonNull
    private Integer inches;

    @Column(name = "sales")
    @NonNull
    private Integer sales;

    @Version
    private long version;

    @Override
    public int compare(Television tv1, Television tv2) {
        return tv1.getSales().compareTo(tv2.getSales());
    }
}
