package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.enums.Parameters;

import java.util.ArrayList;
import java.util.HashMap;

public class ChainCreator {

    public Handler createChainOfMatchers(HashMap<Parameters, String> params) {
        ArrayList<Handler> handlersList = new ArrayList<>();

        if (params.containsKey(Parameters.RESTAURANT_NAME)) {
            handlersList.add(new RestaurantNameMatcher());
        }
        if (params.containsKey(Parameters.CUISINE)) {
            handlersList.add(new CuisineMatcher());
        }
        if (params.containsKey(Parameters.DISTANCE)) {
            handlersList.add(new DistanceMatcher());
        }
        if (params.containsKey(Parameters.RATING)) {
            handlersList.add(new RatingMatcher());
        }
        if (params.containsKey(Parameters.PRICE)) {
            handlersList.add(new PriceMatcher());
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
