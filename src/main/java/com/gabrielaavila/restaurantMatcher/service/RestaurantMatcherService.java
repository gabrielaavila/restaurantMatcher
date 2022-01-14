package com.gabrielaavila.restaurantMatcher.service;

import com.gabrielaavila.restaurantMatcher.chainOfHandlers.ChainCreatorService;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.Handler;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static java.util.Comparator.reverseOrder;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
public class RestaurantMatcherService {

    private static final int RESULTS_LIMIT = 5;

    @Autowired
    private ChainCreatorService chainCreator;
    
    @Autowired
    private RestaurantDataStructure restaurantInfo;

    public List<Restaurant> getMatchedRestaurants(String restaurantName, String cuisine, Integer distance,
                                                  Integer rating, Integer price) {

        HashMap<Parameters, String> params = loadParams(restaurantName, cuisine, distance, rating, price);
        Handler handler = chainCreator.createChainOfMatchers(params, restaurantInfo);

        if (isNull(handler)) {
            return Collections.emptyList();
        }
        HashSet<Integer> resultIndexes = handler.execute(new HashSet<>(), params);
        
        return getFilteredRestaurantList(resultIndexes);
    }

    private List<Restaurant> getFilteredRestaurantList(HashSet<Integer> resultIndexes) {
        return resultIndexes.stream()
                .map(i -> restaurantInfo.getRestaurants().get(i))
                .sorted(compareRestaurants())
                .limit(RESULTS_LIMIT)
                .collect(toList());
    }

    private HashMap<Parameters, String> loadParams(String restaurantName, String cuisine, Integer distance,
                                                   Integer rating, Integer price) {
        HashMap<Parameters, String> params = new HashMap<>();

        if (nonNull(restaurantName)) {
            params.put(Parameters.RESTAURANT_NAME, restaurantName);
        }
        if (nonNull(cuisine)) {
            params.put(Parameters.CUISINE, cuisine);
        }
        if (nonNull(distance)) {
            params.put(Parameters.DISTANCE, distance.toString());
        }
        if (nonNull(rating)) {
            params.put(Parameters.RATING, rating.toString());
        }
        if (nonNull(price)) {
            params.put(Parameters.PRICE, price.toString());
        }
        return params;
    }

    private Comparator<Restaurant> compareRestaurants() {
        return Comparator.comparing(Restaurant::getDistance)
                .thenComparing(Restaurant::getRating, reverseOrder())
                .thenComparing(Restaurant::getPrice);
    }


}
