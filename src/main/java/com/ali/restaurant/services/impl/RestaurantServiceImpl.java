package com.ali.restaurant.services.impl;

import com.ali.restaurant.domain.GeoLocation;
import com.ali.restaurant.domain.RestaurantCreateUpdateRequest;
import com.ali.restaurant.domain.entities.Address;
import com.ali.restaurant.domain.entities.Photo;
import com.ali.restaurant.domain.entities.Restaurant;
import com.ali.restaurant.repositories.RestaurantRepository;
import com.ali.restaurant.services.GeoLocationService;
import com.ali.restaurant.services.RestaurantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final GeoLocationService geoLocationService;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant creatRestaurant(RestaurantCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationService.getLocate(address);
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(),geoLocation.getLongitude());

        List<String> photosId = request.getPhotoIds();
        List<Photo> photos = photosId.stream().map(photoUrl-> Photo.builder()
                .url(photoUrl)
                .uploadDate(LocalDateTime.now())
                .build()).toList();
        Restaurant restaurant = Restaurant.builder().
                name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .address(address)
                .geoLocation(geoPoint)
                .operatingHours(request.getOperatingHours())
                .averageRating(0f)
                .photos(photos).
                build();
        return restaurantRepository.save(restaurant);
    }
}
