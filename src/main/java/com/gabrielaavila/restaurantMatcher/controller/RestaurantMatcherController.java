package com.gabrielaavila.restaurantMatcher.controller;

import com.gabrielaavila.restaurantMatcher.domain.Restaurant;
import com.gabrielaavila.restaurantMatcher.service.RestaurantMatcherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("restaurant-matcher")
@Api(value = "Restaurant Matcher Controller")
@Validated
public class RestaurantMatcherController {
    @Autowired
    private RestaurantMatcherService matcherService;

    @GetMapping("/matches")
    public ResponseEntity<List<Restaurant>> getMatchedRestaurants(
            @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) @Min(value = 1, message = "Min distance allowed is 1") @Max(value = 10, message = "Max distance allowed is 10") Integer distance,
            @RequestParam(required = false) @Min(value = 1, message = "Min rating allowed is 1") @Max(value = 5, message = "Max rating allowed is 5") Integer rating,
            @RequestParam(required = false) @Min(value = 10, message = "Min price allowed is 10") @Max(value = 50, message = "Max price allowed is 50") Integer price) {
        return new ResponseEntity<>(
                matcherService.getMatchedRestaurants(restaurantName, cuisine, distance, rating, price), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex) {
        return "Something went wrong! Exception is: "+ ex.toString();
    }
}
