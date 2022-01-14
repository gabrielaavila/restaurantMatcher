package com.gabrielaavila.restaurantMatcher.enums;

public enum Parameters {
    RESTAURANT_NAME(0),
    CUISINE(1),
    DISTANCE(2),
    RATING(3),
    PRICE(4);

    private int step;

    Parameters(int step) {
        this.step = step;
    }
}
