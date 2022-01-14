package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.RATING;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getParams;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingMatcherTest {

    @Mock
    private RestaurantDataStructure restaurantsInfo;

    @InjectMocks
    private RatingMatcher ratingMatcher;

    @BeforeEach
    void setUp() {
        ratingMatcher.setNext(null);
    }

    @Test
    public void testWhenResultsIsEmptyThenFilterByRestaurantList() {
        HashMap<Parameters, String> params = getParams(RATING, "3");

        when(restaurantsInfo.getRestaurants()).thenReturn(getRestaurantList());

        HashSet<Integer> results = ratingMatcher.execute(new HashSet<>(), params);

        assertThat(results).isNotEmpty().hasSize(3).containsExactlyInAnyOrder(0,1,2);
    }

    @Test
    public void testWhenResultsIsNotEmptyThenFilterByResultsList() {
        HashMap<Parameters, String> params = getParams(RATING, "4");
        HashSet<Integer> results = new HashSet<>(Arrays.asList(0,1,3));

        when(restaurantsInfo.getRestaurants()).thenReturn(getRestaurantList());

        HashSet<Integer> filteredResults = ratingMatcher.execute(results, params);

        assertThat(filteredResults).isNotEmpty().hasSize(1).containsExactlyInAnyOrder(1);

    }

}