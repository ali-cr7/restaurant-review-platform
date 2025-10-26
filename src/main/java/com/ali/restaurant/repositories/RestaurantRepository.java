package com.ali.restaurant.repositories;

import com.ali.restaurant.domain.entities.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// Temporarily commented out to resolve startup issues
 @Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurant,String> {
}
