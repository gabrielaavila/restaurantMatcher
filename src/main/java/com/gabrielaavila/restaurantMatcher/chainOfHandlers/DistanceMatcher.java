package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.DISTANCE;

public class DistanceMatcher extends Matcher {

    @Override
    public HashSet<Integer> execute(@NonNull HashSet<Integer> results, HashMap<Parameters, String> params) {
        Integer distance = Integer.parseInt(params.get(DISTANCE));

        if (results.isEmpty()) {
            results = filterRestaurantsByDistance(distance);
        } else {
            filterResultsByDistance(distance, results);
        }

        return executeNext(results, params);
    }

    private HashSet<Integer> filterRestaurantsByDistance(Integer distance) {
        HashSet<Integer> results =  new HashSet<>();
        ArrayList<Restaurant> restaurants = getRestaurants();

        for (int i=0; i < restaurants.size(); i++) {
            //restaurant distance should be equal or smaller than given distance
            if (restaurants.get(i).getDistance() <= distance) {
                results.add(i);
            }
        }
        return results;
    }

    private void filterResultsByDistance(Integer distance, HashSet<Integer> results) {
        ArrayList<Restaurant> restaurants = getRestaurants();
        results.removeIf(index -> restaurants.get(index).getDistance() > distance);
    }

}
