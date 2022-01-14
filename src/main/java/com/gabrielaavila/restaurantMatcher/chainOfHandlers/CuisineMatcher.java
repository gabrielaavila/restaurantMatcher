package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.CUISINE;

public class CuisineMatcher extends Matcher {

    public CuisineMatcher(RestaurantDataStructure restaurantsInfo) {
        super(restaurantsInfo);
    }

    @Override
    public HashSet<Integer> execute(@NonNull HashSet<Integer> results, HashMap<Parameters, String> params) {
        HashSet<Integer> cuisineList = getRestaurantsByCuisine().searchWordsIndexByPrefix(params.get(CUISINE));

        results = filterResults(results, cuisineList);

        return executeNext(results, params);

    }

    private HashSet<Integer> filterResults(HashSet<Integer> results, HashSet<Integer> cuisineList) {
        if (results.isEmpty()) {
            return cuisineList;
        }

        results.removeIf(index -> !cuisineList.contains(index));
        return results;
    }
}
