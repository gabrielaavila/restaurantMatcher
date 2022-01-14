package com.gabrielaavila.restaurantMatcher.mothers;

import com.gabrielaavila.restaurantMatcher.chainOfHandlers.CuisineMatcher;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.DistanceMatcher;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.Handler;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.PriceMatcher;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.RatingMatcher;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.RestaurantNameMatcher;

public class ChainCreatorMother {
    private ChainCreatorMother() {
    }

    public static Handler getCompleteChain() {
        Handler head = new RestaurantNameMatcher();
        Handler tail = head.setNext(new CuisineMatcher());
        tail = tail.setNext(new DistanceMatcher());
        tail = tail.setNext(new RatingMatcher());
        tail = tail.setNext(new PriceMatcher());
        return head;
    }
}
