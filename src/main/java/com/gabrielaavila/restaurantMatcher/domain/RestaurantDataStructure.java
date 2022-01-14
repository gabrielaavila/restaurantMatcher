package com.gabrielaavila.restaurantMatcher.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantDataStructure {

    private ArrayList<Restaurant> restaurants;
    private Map<Integer, Cuisine> cuisines;
    private Trie restaurantsByName;
    private Trie restaurantsByCuisine;
}
