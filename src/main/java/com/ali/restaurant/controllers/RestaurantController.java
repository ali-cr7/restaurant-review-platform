package com.ali.restaurant.controllers;

import com.ali.restaurant.domain.RestaurantCreateUpdateRequest;
import com.ali.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.ali.restaurant.domain.dtos.RestaurantDto;
import com.ali.restaurant.domain.entities.Restaurant;
import com.ali.restaurant.mappers.RestaurantMapper;
import com.ali.restaurant.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantMapper  restaurantMapper;
    private final RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant (
         @Valid @RequestBody RestaurantCreateUpdateRequestDto request
            ){
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest =restaurantMapper.
                toRestaurantCreateUpdateRequest(request);
        Restaurant restaurant= restaurantService.creatRestaurant(restaurantCreateUpdateRequest);
        RestaurantDto createdRestaurantDto= restaurantMapper.toRestaurantDto(restaurant);
        return ResponseEntity.ok(createdRestaurantDto);
    }
}
