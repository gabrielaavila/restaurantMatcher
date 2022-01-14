package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.util.Objects.nonNull;

public abstract class Matcher implements Handler {
    private Handler nextMatcher;

    @Autowired
    public RestaurantDataStructure restaurantsInfo;

    public Matcher(RestaurantDataStructure restaurantsInfo) {
        this.restaurantsInfo = restaurantsInfo;
    }

    @Override
    public Handler setNext(Handler next) {
        this.nextMatcher = next;
        return this.nextMatcher;
    }

    @Override
    public Handler getNext() {
        return this.nextMatcher;
    }

    //move to next step only if still are results to be processed
    protected HashSet<Integer> executeNext(HashSet<Integer> results, HashMap<Parameters, String> params) {
        return results.isEmpty() ? results :
                nonNull(this.getNext()) ? this.getNext().execute(results, params) : results;
    }

    protected ArrayList<Restaurant> getRestaurants() {
        return restaurantsInfo.getRestaurants();
    }

    protected Trie getRestaurantsByName() {
        return restaurantsInfo.getRestaurantsByName();
    }

    protected Trie getRestaurantsByCuisine() {
        return restaurantsInfo.getRestaurantsByCuisine();
    }
}
