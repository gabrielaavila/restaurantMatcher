package com.gabrielaavila.restaurantMatcher.config;

import com.gabrielaavila.restaurantMatcher.domain.RestaurantDataStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
public class RestaurantDataStructureConfig {

    @Value("${restaurants.filepath}")
    private String restaurantsFilepath;

    @Value("${cuisines.filepath}")
    private String cuisinesFilepath;

    @Autowired
    private LoaderService loaderService;

    @Bean
    public RestaurantDataStructure getRestaurantDataStructures() {
        RestaurantDataStructure restaurantInfo = new RestaurantDataStructure();
        restaurantInfo.setRestaurants(loaderService.loadRestaurants(restaurantsFilepath));
        restaurantInfo.setCuisines(loaderService.loadCuisines(cuisinesFilepath));
        loaderService.loadTries(restaurantInfo);

        return restaurantInfo;
    }
}
