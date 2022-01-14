package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ChainCreatorService {

    public Handler createChainOfMatchers(HashMap<Parameters, String> params, RestaurantDataStructure restaurantInfo) {
        ArrayList<Handler> handlersList = new ArrayList<>();

        if (params.containsKey(Parameters.RESTAURANT_NAME)) {
            handlersList.add(new RestaurantNameMatcher(restaurantInfo));
        }
        if (params.containsKey(Parameters.CUISINE)) {
            handlersList.add(new CuisineMatcher(restaurantInfo));
        }
        if (params.containsKey(Parameters.DISTANCE)) {
            handlersList.add(new DistanceMatcher(restaurantInfo));
        }
        if (params.containsKey(Parameters.RATING)) {
            handlersList.add(new RatingMatcher(restaurantInfo));
        }
        if (params.containsKey(Parameters.PRICE)) {
            handlersList.add(new PriceMatcher(restaurantInfo));
        }

        return setChainOfHandlers(handlersList);

    }

    private Handler setChainOfHandlers(ArrayList<Handler> list) {
        if (list.isEmpty()) {
            return null;
        }

        Handler firstHandler = list.get(0);
        Handler currentHandler = firstHandler;
        for (int i = 1; i < list.size(); i++) {
            currentHandler = currentHandler.setNext(list.get(i));
        }
        return firstHandler;
    }
}
