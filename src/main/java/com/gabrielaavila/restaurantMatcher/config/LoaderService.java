package com.gabrielaavila.restaurantMatcher.config;

import com.gabrielaavila.restaurantMatcher.domain.Cuisine;
import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import com.gabrielaavila.restaurantMatcher.domain.Trie;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LoaderService {

    public static final String NON_ALPHABETIC = "[^a-zA-Z]";

    public RestaurantDataStructure loadTries(RestaurantDataStructure restaurantDataStructure) {

        ArrayList<Restaurant> restaurants = restaurantDataStructure.getRestaurants();
        Map<Integer, Cuisine> cuisines = restaurantDataStructure.getCuisines();
        Trie restaurantsByCuisine = new Trie();
        Trie restaurantsByName = new Trie();


        for (int i = 0; i < restaurants.size(); i++) {
            // add restaurant into cuisine trie
            restaurantsByCuisine.insert(cuisines.get(restaurants.get(i).getCuisineId()).getName(), i);

            // split restaurant name into words
            List<String> words = formatRestaurantName(restaurants.get(i).getName());

            // add each one into restaurantTrie
            for (String word : words) {
                restaurantsByName.insert(word, i);
            }
        }


        restaurantDataStructure.setRestaurantsByName(restaurantsByName);
        restaurantDataStructure.setRestaurantsByCuisine(restaurantsByCuisine);
        return restaurantDataStructure;
    }

    public ArrayList<Restaurant> loadRestaurants(String filepath) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try (Reader reader = getReader(filepath)) {
            restaurants = new CsvToBeanBuilder<Restaurant>(reader)
                    .withType(Restaurant.class)
                    .build().stream().collect(Collectors.toCollection(ArrayList::new));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    private List<String> formatRestaurantName(String word) {
        String[] words = word.trim().split("\\s+");

        return Arrays.stream(words).map(w -> w.replaceAll(NON_ALPHABETIC, "")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    public Map<Integer, Cuisine> loadCuisines(String filepath) {
        Map<Integer, Cuisine> cuisines = new HashMap<>();
        try (Reader reader = getReader(filepath)) {
            cuisines = new CsvToBeanBuilder<Cuisine>(reader)
                    .withType(Cuisine.class)
                    .build().stream()
                    .collect(Collectors.toMap(Cuisine::getId, Function.identity()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cuisines;
    }

    private static Reader getReader(String filename) throws IOException {
        return Files.newBufferedReader(Paths.get(filename));
    }
}
