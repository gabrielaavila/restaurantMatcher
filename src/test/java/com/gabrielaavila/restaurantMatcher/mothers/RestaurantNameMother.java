package com.gabrielaavila.restaurantMatcher.mothers;

import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;

import java.util.ArrayList;

public class RestaurantNameMother {
    private RestaurantNameMother() {
    }

    public static ArrayList<Restaurant> getRestaurantList() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(getRestaurant("banana", 1, 2, 10, 3));
        restaurants.add(getRestaurant("maca rest", 2, 1, 10, 5));
        restaurants.add(getRestaurant("bananas rest", 2, 3, 15, 4));
        restaurants.add(getRestaurant("laranja", 3, 4, 20, 2));

        return restaurants;
    }

    public static Restaurant getRestaurant(String name, Integer cuisineId, Integer distance, Integer price, Integer rating) {
        Restaurant rest = new Restaurant();
        rest.setName(name);
        rest.setCuisineId(cuisineId);
        rest.setDistance(distance);
        rest.setPrice(price);
        rest.setRating(rating);
        return rest;
    }

    public static Trie loadRestaurantNameTrie(ArrayList<Restaurant> restaurants) {
        Trie restaurantTrie = new Trie();

        for (int i = 0; i < restaurants.size(); i++) {
            // split restaurant name into words
            String[] words = restaurants.get(i).getName().trim().split("\\s+");

            // add each one into restaurantTrie
            for (String word : words) {
                restaurantTrie.insert(word, i);
            }
        }
        return restaurantTrie;
    }


}
