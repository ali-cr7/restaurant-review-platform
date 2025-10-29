package com.ali.restaurant.services.impl;

import com.ali.restaurant.domain.GeoLocation;
import com.ali.restaurant.domain.RestaurantCreateUpdateRequest;
import com.ali.restaurant.domain.entities.Address;
import com.ali.restaurant.domain.entities.Photo;
import com.ali.restaurant.domain.entities.Restaurant;
import com.ali.restaurant.exceptions.RestaurantNotFoundException;
import com.ali.restaurant.repositories.RestaurantRepository;
import com.ali.restaurant.services.GeoLocationService;
import com.ali.restaurant.services.RestaurantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Restaurant> searchRestaurants(String query, Float minRating, Float latitude, Float longitude, Float radius, Pageable pageable) {
        if(null != minRating && (null == query || query.isEmpty())) {
            return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating, pageable);
        }
        // Normalize min rating to be used in other queries
        Float searchMinRating = minRating == null ? 0f : minRating;
        // If there's a text, search query
        if (query != null && !query.trim().isEmpty()) {
            return restaurantRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
        }
        // If there's a text, search query
        if (query != null && !query.trim().isEmpty()) {
            return restaurantRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
        }
        // If there's a location search
        if (latitude != null && longitude != null && radius != null) {
            return restaurantRepository.findByLocationNear(latitude, longitude, radius, pageable);
        }
        // Otherwise we'll perform a non-location search
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<Restaurant> getRestaurant(String id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest restaurant) {
        Restaurant existingRestaurant = getRestaurant(id)
                .orElseThrow(() ->new RestaurantNotFoundException("Restaurant with ID does not exist"+ id));
        GeoLocation newGeoLocation = geoLocationService.getLocate(
                restaurant.getAddress()
        );
        GeoPoint newGeoPoint = new GeoPoint(newGeoLocation.getLatitude(), newGeoLocation.getLongitude());
        List<String> photoIds = restaurant.getPhotoIds();
        List<Photo> photos = photoIds.stream().map(photoUrl -> Photo.builder()
                .url(photoUrl)
                .uploadDate(LocalDateTime.now())
                .build()).toList();

        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setCuisineType(restaurant.getCuisineType());
        existingRestaurant.setContactInformation(restaurant.getContactInformation());
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setGeoLocation(newGeoPoint);
        existingRestaurant.setOperatingHours(restaurant.getOperatingHours());
        existingRestaurant.setPhotos(photos);

        return restaurantRepository.save(existingRestaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantRepository.deleteById(id);
    }

}
