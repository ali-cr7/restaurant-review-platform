package com.ali.restaurant.services;

import com.ali.restaurant.domain.RestaurantCreateUpdateRequest;
import com.ali.restaurant.domain.entities.Restaurant;

public interface RestaurantService {
    Restaurant creatRestaurant (RestaurantCreateUpdateRequest request);
}
