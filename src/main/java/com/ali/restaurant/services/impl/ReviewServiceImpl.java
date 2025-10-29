package com.ali.restaurant.services.impl;

import com.ali.restaurant.domain.ReviewCreateUpdateRequest;
import com.ali.restaurant.domain.entities.Photo;
import com.ali.restaurant.domain.entities.Restaurant;
import com.ali.restaurant.domain.entities.Review;
import com.ali.restaurant.domain.entities.User;
import com.ali.restaurant.exceptions.RestaurantNotFoundException;
import com.ali.restaurant.exceptions.ReviewNotAllowedException;
import com.ali.restaurant.repositories.RestaurantRepository;
import com.ali.restaurant.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final RestaurantRepository restaurantRepository;
    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review) {
       // Restaurant restaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant =getRestaurantOrThrow(restaurantId);
        // Check if user has already reviewed this restaurant
        boolean hasExistingReview = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));
        if (hasExistingReview) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }
        LocalDateTime now = LocalDateTime.now();
        // Create photos
        List<Photo> photos = review.getPhotoIds().stream().map(url -> {
            Photo photo = new Photo();
            photo.setUrl(url);
            photo.setUploadDate(now);
            return photo;
        }).collect(Collectors.toList());

        Review createdReview = Review.builder()
                .id(UUID.randomUUID().toString())
                .content(review.getContent())
                .rating(review.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();
        restaurant.getReviews().add(createdReview);

        updateRestaurantAverageRating(restaurant);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        // Return the newly created review
        return updatedRestaurant.getReviews().stream()
                .filter(r-> r.getDatePosted().equals(createdReview.getDatePosted()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Error retrieving created review"));

    }

    @Override
    public Page<Review> listReviews(String restaurantId, Pageable pageable) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        List<Review> reviews = restaurant.getReviews();
        Sort sort = pageable.getSort();
        if(sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            boolean isAscending = order.getDirection().isAscending();

            Comparator<Review> comparator = switch (property) {
                case "datePosted" -> Comparator.comparing(Review::getDatePosted);
                case "rating" -> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getDatePosted);
            };

            reviews.sort(isAscending ? comparator : comparator.reversed());

        }
        else {
            reviews.sort(Comparator.comparing(Review::getDatePosted).reversed());
        }
        int start = (int) pageable.getOffset();
        if(start >= reviews.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, reviews.size());
        }
        int end = Math.min((start + pageable.getPageSize()), reviews.size());

        return new PageImpl<>(reviews.subList(start, end), pageable, reviews.size());
    }

    @Override
    public Optional<Review> getReview(String restaurantId, String reviewId) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        return getReviewFromRestaurant(reviewId, restaurant);
    }
    private static Optional<Review> getReviewFromRestaurant(String reviewId, Restaurant restaurant) {
        return restaurant.getReviews()
                .stream()
                .filter(r -> reviewId.equals(r.getId()))
                .findFirst();
    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(
                                "Restaurant with id not found: " + restaurantId
                        )
                );
    }

    private void updateRestaurantAverageRating(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();
        if (reviews.isEmpty()) {
            restaurant.setAverageRating(0.0f);
        } else {
            float averageRating = (float) reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            restaurant.setAverageRating(averageRating);
        }
    }
}
