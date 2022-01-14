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

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.PRICE;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getParams;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceMatcherTest {

    @Mock
    private RestaurantDataStructure restaurantsInfo;

    @InjectMocks
    private PriceMatcher priceMatcher;

    @BeforeEach
    void setUp() {
        priceMatcher.setNext(null);
    }

    @Test
    public void testWhenResultsIsEmptyThenFilterByRestaurantList() {
        HashMap<Parameters, String> params = getParams(PRICE, "11");

        when(restaurantsInfo.getRestaurants()).thenReturn(getRestaurantList());

        HashSet<Integer> results = priceMatcher.execute(new HashSet<>(), params);


        assertThat(results).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(0,1);
    }

    @Test
    public void testWhenResultsIsNotEmptyThenFilterByResultsList() {
        HashMap<Parameters, String> params = getParams(PRICE, "15");
        HashSet<Integer> results = new HashSet<>(Arrays.asList(0,1,3));

        when(restaurantsInfo.getRestaurants()).thenReturn(getRestaurantList());

        HashSet<Integer> filteredResults = priceMatcher.execute(results, params);

        assertThat(filteredResults).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(0,1);

    }

}