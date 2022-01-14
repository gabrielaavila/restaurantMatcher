package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.RATING;

public class RatingMatcher extends Matcher {
    public RatingMatcher(RestaurantDataStructure restaurantsInfo) {
        super(restaurantsInfo);
    }

    @Override
    public HashSet<Integer> execute(@NonNull HashSet<Integer> results, HashMap<Parameters, String> params) {
        Integer rating = Integer.parseInt(params.get(RATING));

        if (results.isEmpty()) {
            results = filterRestaurantsByRating(rating);
        } else {
            filterResultsByRating(rating, results);
        }

        return executeNext(results, params);
    }

    private HashSet<Integer> filterRestaurantsByRating(Integer rating) {
        HashSet<Integer> results =  new HashSet<>();
        ArrayList<Restaurant> restaurants = getRestaurants();

        for (int i=0; i < restaurants.size(); i++) {
            //restaurant rating should be equal or higher than given rating
            if (restaurants.get(i).getRating() >= rating) {
                results.add(i);
            }
        }
        return results;
    }

    private void filterResultsByRating(Integer rating, HashSet<Integer> results) {
        ArrayList<Restaurant> restaurants = getRestaurants();
        results.removeIf(index -> restaurants.get(index).getRating() < rating);
    }
}
