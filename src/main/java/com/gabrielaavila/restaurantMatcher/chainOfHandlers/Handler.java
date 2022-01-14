package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.enums.Parameters;

import java.util.HashMap;
import java.util.HashSet;

public interface Handler {
    Handler setNext(Handler next);

    Handler getNext();
    HashSet<Integer> execute(HashSet<Integer> results, HashMap<Parameters, String> params);
}
