package com.gabrielaavila.restaurantMatcher.mothers;

import com.gabrielaavila.restaurantMatcher.enums.Parameters;

import java.util.HashMap;

public class ParamsMother {
    private static final String restName = "banana";
    private static final String cuisine = "thai";
    private static final String distance = "2";
    private static final String rating = "3";
    private static final String price = "10";

    private ParamsMother() {}

    public static HashMap<Parameters, String> getMapWithAllParameters() {
        HashMap<Parameters, String> params = new HashMap<>();
        params.put(Parameters.RESTAURANT_NAME, restName);
        params.put(Parameters.CUISINE, cuisine);
        params.put(Parameters.DISTANCE, distance);
        params.put(Parameters.RATING, rating);
        params.put(Parameters.PRICE, price);
        return params;
    }

    public static HashMap<Parameters, String> getMapWith1Param() {
        HashMap<Parameters, String> params = new HashMap<>();
        params.put(Parameters.RESTAURANT_NAME, restName);
        return params;
    }

    public static HashMap<Parameters, String> getParams(Parameters param, String value) {
        HashMap<Parameters, String> params = new HashMap<>();
        params.put(param, value);
        return params;
    }

    public static HashMap<Parameters, String> getMapWithNoSequentialParams() {
        HashMap<Parameters, String> params = new HashMap<>();
        params.put(Parameters.CUISINE, cuisine);
        params.put(Parameters.PRICE, price);
        return params;
    }
}
