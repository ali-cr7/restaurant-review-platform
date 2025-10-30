package com.ali.restaurant.services;

import com.ali.restaurant.domain.ReviewCreateUpdateRequest;
import com.ali.restaurant.domain.entities.Review;
import com.ali.restaurant.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {
    Review createReview(User author,
                        String restaurantId,
                        ReviewCreateUpdateRequest review);
    Page<Review> listReviews(String restaurantId, Pageable pageable);
    Optional<Review> getReview(String restaurantId, String reviewId);
    Review updateReview(User user, String restaurantId, String reviewId,
                               ReviewCreateUpdateRequest updatedReview);
    void deleteReview(String restaurantId, String reviewId);
}
