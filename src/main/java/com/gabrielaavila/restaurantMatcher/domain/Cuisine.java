package com.gabrielaavila.restaurantMatcher.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cuisine {
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private String name;
}
