package com.gabrielaavila.restaurantMatcher;

import com.gabrielaavila.restaurantMatcher.config.LoaderService;
import com.gabrielaavila.restaurantMatcher.domain.Cuisine;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.domain.Trie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.gabrielaavila.restaurantMatcher.mothers.CuisineMother.getCuisineList;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurant;
import static com.gabrielaavila.restaurantMatcher.mothers.RestaurantNameMother.getRestaurantList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoaderServiceTest {

    private static final String RESTAURANTS_FILEPATH = "src/test/resources/restaurants.csv";
    private static final String CUISINES_FILEPATH = "src/test/resources/cuisines.csv";

    private LoaderService service = new LoaderService();

    @Test
    public void testLoadRestaurantsSuccessfully() {
        ArrayList<Restaurant> restaurants = service.loadRestaurants(RESTAURANTS_FILEPATH);

        assertThat(restaurants).isNotEmpty().hasSize(200)
                .extracting(Restaurant::getName)
                .contains("Deliciousgenix")
                .contains("Cuts Delicious");
    }

    @Test
    public void testLoadCuisinesSuccessfully() {
        Map<Integer, Cuisine> cuisines = service.loadCuisines(CUISINES_FILEPATH);

        assertThat(cuisines).isNotEmpty().hasSize(3).containsOnlyKeys(1,2,3);
    }

    @Test
    public void testLoadTriesSuccessfully() {
        RestaurantDataStructure dataStructure = new RestaurantDataStructure();
        dataStructure.setRestaurants(getRestaurantList());
        dataStructure.setCuisines(getCuisineList());

        dataStructure = service.loadTries(dataStructure);

        assertNotNull(dataStructure);
        Trie restaurantByName = dataStructure.getRestaurantsByName();

        assertThat(restaurantByName.searchWordsIndexByPrefix("banana")).isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrder(0,2);
        assertThat(restaurantByName.searchWordsIndexByPrefix("re")).isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrder(1,2);
        assertThat(restaurantByName.searchWordsIndexByPrefix("m")).isNotEmpty()
                .hasSize(1)
                .containsExactlyInAnyOrder(1);
        assertThat(restaurantByName.searchWordsIndexByPrefix("ana")).isEmpty();


        Trie restaurantsByCuisine = dataStructure.getRestaurantsByCuisine();

        assertThat(restaurantsByCuisine.searchWordsIndexByPrefix("thai")).isNotEmpty()
                .hasSize(1)
                .containsExactlyInAnyOrder(0);

        assertThat(restaurantsByCuisine.searchWordsIndexByPrefix("ind")).isNotEmpty()
                .hasSize(3)
                .containsExactlyInAnyOrder(1,2,3);

        assertThat(restaurantsByCuisine.searchWordsIndexByPrefix("sian")).isEmpty();
    }

    @Test
    public void testLoadTriesWordsWithSpecialCharactersSuccessfully() {
        RestaurantDataStructure dataStructure = new RestaurantDataStructure();
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(getRestaurant("banana .rest/", 1, 2, 10, 3));
        dataStructure.setRestaurants(restaurants);
        dataStructure.setCuisines(getCuisineList());

        dataStructure = service.loadTries(dataStructure);

        assertNotNull(dataStructure);
        Trie restaurantByName = dataStructure.getRestaurantsByName();

        assertThat(restaurantByName.searchWordsIndexByPrefix("banana")).isNotEmpty()
                .hasSize(1);

        assertThat(restaurantByName.searchWordsIndexByPrefix("ma")).isEmpty();
    }
}