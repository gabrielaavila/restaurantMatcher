package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.PRICE;

public class PriceMatcher extends Matcher {
    public PriceMatcher(RestaurantDataStructure restaurantsInfo) {
        super(restaurantsInfo);
    }

    @Override
    public HashSet<Integer> execute(@NonNull HashSet<Integer> results, HashMap<Parameters, String> params) {

        Integer price = Integer.parseInt(params.get(PRICE));

        if (results.isEmpty()) {
            results = filterRestaurantsByPrice(price);
        } else {
            filterResultsByPrice(price, results);
        }

        return executeNext(results, params);
    }

    private HashSet<Integer> filterRestaurantsByPrice(Integer price) {
        HashSet<Integer> results =  new HashSet<>();
        ArrayList<Restaurant> restaurants = getRestaurants();

        for (int i=0; i < restaurants.size(); i++) {
            //restaurant price should be equal or smaller than given price
            if (restaurants.get(i).getPrice() <= price) {
                results.add(i);
            }
        }
        return results;
    }

    private void filterResultsByPrice(Integer price, HashSet<Integer> results) {
        ArrayList<Restaurant> restaurants = getRestaurants();
        results.removeIf(index -> restaurants.get(index).getPrice() > price);
    }
}
