package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.RESTAURANT_NAME;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getParams;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.loadRestaurantNameTrie;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantNameMatcherTest {
    @Mock
    private RestaurantDataStructure restaurantsInfo;

    @InjectMocks
    private RestaurantNameMatcher restaurantNameMatcher;

    @BeforeEach
    void setUp() {
        restaurantNameMatcher.setNext(null);
    }

    @Test
    public void testWhenSearchByPrefixThenResultsFound() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        String restaurantName = "banana";
        Trie restByName = loadRestaurantNameTrie(restaurants);
        HashMap<Parameters, String> params = getParams(RESTAURANT_NAME, restaurantName);

        when(restaurantsInfo.getRestaurantsByName()).thenReturn(restByName);

        HashSet<Integer> results = restaurantNameMatcher.execute(new HashSet<>(), params);

        assertThat(results).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(0, 2);

    }

    @Test
    public void testWhenGivenNameNotMatchesThenResultsNotFound() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        String restaurantName = "restaurant";
        Trie restByName = loadRestaurantNameTrie(restaurants);
        HashMap<Parameters, String> params = getParams(RESTAURANT_NAME, restaurantName);

        when(restaurantsInfo.getRestaurantsByName()).thenReturn(restByName);

        HashSet<Integer> results = restaurantNameMatcher.execute(new HashSet<>(), params);

        assertThat(results).isEmpty();
    }

    @Test
    public void testWhenSearchBySecondWordPrefixThenResultsFound() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        String restaurantName = "rest";
        Trie restByName = loadRestaurantNameTrie(restaurants);
        HashMap<Parameters, String> params = getParams(RESTAURANT_NAME, restaurantName);

        when(restaurantsInfo.getRestaurantsByName()).thenReturn(restByName);

        HashSet<Integer> results = restaurantNameMatcher.execute(new HashSet<>(), params);

        assertThat(results).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(1, 2);
    }
}