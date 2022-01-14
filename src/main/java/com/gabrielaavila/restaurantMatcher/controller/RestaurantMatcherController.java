package com.gabrielaavila.restaurantMatcher.controller;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.service.RestaurantMatcherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("restaurant-matcher")
@Api(value = "Restaurant Matcher Controller")
public class RestaurantMatcherController {
    @Autowired
    private RestaurantMatcherService matcherService;

    @GetMapping("/matches")
    public ResponseEntity<List<Restaurant>> getMatchedRestaurants(@RequestParam(required = false) String restaurantName,
                                                                  @RequestParam(required = false) String cuisine,
                                                                  @RequestParam(required = false) Integer distance,
                                                                  @RequestParam(required = false) Integer rating,
                                                                  @RequestParam(required = false) Integer price) {
        return new ResponseEntity<>(
                matcherService.getMatchedRestaurants(restaurantName, cuisine, distance, rating, price), HttpStatus.OK);
    }

}
