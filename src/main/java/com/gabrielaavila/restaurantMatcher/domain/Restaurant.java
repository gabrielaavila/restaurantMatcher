package com.gabrielaavila.restaurantMatcher.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Restaurant {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "customer_rating")
    private Integer rating;
    @CsvBindByName(column = "distance")
    private Integer distance;
    @CsvBindByName(column = "price")
    private Integer price;
    @CsvBindByName(column = "cuisine_id")
    private Integer cuisineId;

}
