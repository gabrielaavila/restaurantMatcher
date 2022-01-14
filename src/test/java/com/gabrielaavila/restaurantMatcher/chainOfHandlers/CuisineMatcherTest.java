package com.gabrielaavila.restaurantMatcher.chainOfHandlers;

import com.gabrielaavila.restaurantMatcher.config.LoaderService;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.enums.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static com.gabrielaavila.restaurantMatcher.enums.Parameters.CUISINE;
import static com.gabrielaavila.restaurantMatcher.mothers.CuisineMother.getCuisineList;
import static com.gabrielaavila.restaurantMatcher.mothers.CuisineMother.loadRestByCuisineTrie;
import static com.gabrielaavila.restaurantMatcher.mothers.ParamsMother.getParams;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuisineMatcherTest {
    @Mock
    private RestaurantDataStructure restaurantsInfo;

    @InjectMocks
    private CuisineMatcher cuisineMatcher;

    @BeforeEach
    void setUp() {
        cuisineMatcher.setNext(null);
    }

    @Test
    public void testWhenMatchesFoundForGivenWordAndNoResultsToFilterThenReturnAllMatches() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        Trie restByCuisine = loadRestByCuisineTrie(getCuisineList(), restaurants);
        HashMap<Parameters, String> params = getParams(CUISINE, "indian");

        when(restaurantsInfo.getRestaurantsByCuisine()).thenReturn(restByCuisine);

        HashSet<Integer> results = cuisineMatcher.execute(new HashSet<>(), params);
        assertThat(results).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(1,2);
    }

    @Test
    public void testWhenMatchesNotFoundForGivenWordThenReturnEmptySet() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        Trie restByCuisine = loadRestByCuisineTrie(getCuisineList(), restaurants);
        HashMap<Parameters, String> params = getParams(CUISINE, "brazilian");

        when(restaurantsInfo.getRestaurantsByCuisine()).thenReturn(restByCuisine);

        HashSet<Integer> results = cuisineMatcher.execute(new HashSet<>(), params);
        assertThat(results).isEmpty();
    }

    @Test
    public void testWhenMatchesFoundForGivenWordAndAreResultsToFilterThenReturnResultsFiltered() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        Trie restByCuisine = loadRestByCuisineTrie(getCuisineList(), restaurants);
        HashMap<Parameters, String> params = getParams(CUISINE, "ind");
        HashSet<Integer> results = new HashSet<>(Arrays.asList(0,1,3));


        when(restaurantsInfo.getRestaurantsByCuisine()).thenReturn(restByCuisine);

        HashSet<Integer> filteredResults = cuisineMatcher.execute(results, params);
        assertThat(filteredResults).isNotEmpty().hasSize(2).containsExactlyInAnyOrder(1,3);
    }

    @Test
    public void testWhenItemsFoundDoesNotMatchResultsSetThenReturnEmptySet() {
        ArrayList<Restaurant> restaurants = getRestaurantList();
        Trie restByCuisine = loadRestByCuisineTrie(getCuisineList(), restaurants);
        HashMap<Parameters, String> params = getParams(CUISINE, "thai");
        HashSet<Integer> results = new HashSet<>(Arrays.asList(1,2,3));

        when(restaurantsInfo.getRestaurantsByCuisine()).thenReturn(restByCuisine);

        HashSet<Integer> filteredResults = cuisineMatcher.execute(results, params);
        assertThat(filteredResults).isEmpty();
    }

}