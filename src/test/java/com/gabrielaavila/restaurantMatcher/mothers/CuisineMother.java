package com.gabrielaavila.restaurantMatcher.mothers;

import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.gabrielaavila.restaurantMatcher.domain.Cuisine;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuisineMother {
    private CuisineMother() {
    }

    public static HashMap<Integer, Cuisine> getCuisineList() {
        HashMap<Integer, Cuisine> cuisines = new HashMap<>();
        cuisines.put(1, getCuisine("Thai", 1));
        cuisines.put(2, getCuisine("Indian", 2));
        cuisines.put(3, getCuisine("Indonesian", 3));

        return cuisines;
    }

    private static Cuisine getCuisine(String name, Integer cuisineId) {
        Cuisine cuisine = new Cuisine();
        cuisine.setName(name);
        cuisine.setId(cuisineId);
        return cuisine;
    }

    public static Trie loadRestByCuisineTrie(Map<Integer, Cuisine> cuisines, ArrayList<Restaurant> restaurants) {
        Trie restByCuisine = new Trie();

        for (int i = 0; i < restaurants.size(); i++) {
            restByCuisine.insert(cuisines.get(restaurants.get(i).getCuisineId()).getName(), i);
        }
        return restByCuisine;
    }


}
