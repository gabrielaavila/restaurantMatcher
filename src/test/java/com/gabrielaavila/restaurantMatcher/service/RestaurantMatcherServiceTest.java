package com.gabrielaavila.restaurantMatcher.service;

import com.gabrielaavila.restaurantMatcher.chainOfHandlers.ChainCreator;
import com.gabrielaavila.restaurantMatcher.chainOfHandlers.RestaurantNameMatcher;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurant;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantMatcherServiceTest {
    @Mock
    private ChainCreator chainCreator;

    @Mock
    private RestaurantNameMatcher handler;

    @Mock
    private RestaurantDataStructure restaurantInfo;

    @InjectMocks
    private RestaurantMatcherService service;

    @Test
    public void testGivenRestNameThenFilterByRestName() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        HashSet<Integer> results = new HashSet<>(Arrays.asList(1,2));

        when(chainCreator.createChainOfMatchers(any())).thenReturn(handler);
        when(restaurantInfo.getRestaurants()).thenReturn(restaurants);
        when(handler.execute(any(), any())).thenReturn(results);

        List<Restaurant> result = service
                .getMatchedRestaurants("banana", null, null, null, null);

        assertThat(result).isNotEmpty()
                .hasSize(2)
                .extracting(Restaurant::getName).containsExactlyInAnyOrder("maca rest", "bananas rest");
    }


    @Test
    public void testGivenEqualResultsThenSortCorrectly() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(getRestaurant("rest1", 3, 3, 20, 2));
        restaurants.add(getRestaurant("rest2", 3, 4, 20, 2));
        restaurants.add(getRestaurant("rest3", 3, 4, 20, 3));
        restaurants.add(getRestaurant("rest4", 3, 4, 15, 3));

        HashSet<Integer> results = new HashSet<>(Arrays.asList(0,1,2,3));

        when(chainCreator.createChainOfMatchers(any())).thenReturn(handler);
        when(restaurantInfo.getRestaurants()).thenReturn(restaurants);
        when(handler.execute(any(), any())).thenReturn(results);

        List<Restaurant> result = service
                .getMatchedRestaurants("banana", null, null, null, null);

        assertThat(result).isNotEmpty()
                .hasSize(4)
                .extracting(Restaurant::getName).containsExactly("rest1", "rest4", "rest3", "rest2");
    }

    @Test
    public void testGivenMoreThan5ResultsThenSortAndFilterCorrectly() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(getRestaurant("rest1", 3, 3, 15, 4));
        restaurants.add(getRestaurant("rest2", 3, 3, 20, 3));
        restaurants.add(getRestaurant("rest3", 3, 3, 10, 3));
        restaurants.add(getRestaurant("rest4", 3, 5, 15, 1));
        restaurants.add(getRestaurant("rest5", 3, 5, 15, 5));
        restaurants.add(getRestaurant("rest6", 3, 5, 15, 4));
        restaurants.add(getRestaurant("rest7", 3, 5, 50, 3));

        //rest1, rest3, rest2, rest5, rest6

        HashSet<Integer> results = new HashSet<>(Arrays.asList(0,1,2,3,4,5,6));

        when(chainCreator.createChainOfMatchers(any())).thenReturn(handler);
        when(restaurantInfo.getRestaurants()).thenReturn(restaurants);
        when(handler.execute(any(), any())).thenReturn(results);

        List<Restaurant> result = service
                .getMatchedRestaurants("banana", null, null, null, null);

        assertThat(result).isNotEmpty()
                .hasSize(5)
                .extracting(Restaurant::getName).containsExactly("rest1", "rest3", "rest2", "rest5", "rest6");
    }

}