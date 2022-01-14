package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.AllArgsConstructor;
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

        //needs to filter from results or we can consider that results always come to this handler as an empty hashset?
        results = getRestaurantsByName().searchWordsIndexByPrefix(params.get(RESTAURANT_NAME));

        return executeNext(results, params);
    }
}
