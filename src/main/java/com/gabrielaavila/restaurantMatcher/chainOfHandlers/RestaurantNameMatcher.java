package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.RESTAURANT_NAME;

public class RestaurantNameMatcher extends Matcher {

    public RestaurantNameMatcher(RestaurantDataStructure restaurantsInfo) {
        super(restaurantsInfo);
    }

    @Override
    public HashSet<Integer> execute(@NonNull HashSet<Integer> results, HashMap<Parameters, String> params) {

        results = getRestaurantsByName().searchWordsIndexByPrefix(params.get(RESTAURANT_NAME));

        return executeNext(results, params);
    }
}
