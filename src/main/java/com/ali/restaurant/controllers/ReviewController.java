package com.ali.restaurant.controllers;

import com.ali.restaurant.domain.ReviewCreateUpdateRequest;
import com.ali.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import com.ali.restaurant.domain.dtos.ReviewDto;
import com.ali.restaurant.domain.entities.Review;
import com.ali.restaurant.domain.entities.User;
import com.ali.restaurant.mappers.ReviewMapper;
import com.ali.restaurant.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto>  createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt
    )   {
        ReviewCreateUpdateRequest ReviewCreateUpdateRequest =
                reviewMapper.toReviewCreateUpdateRequest(review);
        User user = jwtToUser(jwt);
        // Create the review
        Review createdReview = reviewService.createReview(
                user, restaurantId, ReviewCreateUpdateRequest);
        // Return the created review as DTO
        return ResponseEntity.ok(reviewMapper.toDto(createdReview));

    }

    private User jwtToUser(Jwt jwt) {
        return new User(
                jwt.getSubject(), // User's unique ID
                jwt.getClaimAsString("preferred_username"), // Username
                jwt.getClaimAsString("given_name"), // First name
                jwt.getClaimAsString("family_name") // Last name
        );
    }

    @GetMapping
    public Page<ReviewDto> listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return reviewService
                .listReviews(restaurantId, pageable)
                .map(reviewMapper::toDto);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getRestaurantReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId) {
        return reviewService
                .getReview(restaurantId, reviewId)
                .map(reviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt
    ){
        // Convert the DTO to domain object
        ReviewCreateUpdateRequest reviewCreateUpdateRequest =
                reviewMapper.toReviewCreateUpdateRequest(review);
        User user = jwtToUser(jwt);
        // Call service to perform update
        Review updatedReview = reviewService.updateReview(
                user,
                restaurantId,
                reviewId,
                reviewCreateUpdateRequest);
        // Return updated review
        return ResponseEntity.ok(reviewMapper.toDto(updatedReview));

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
    )
    {
        reviewService.deleteReview(restaurantId, reviewId);
        return ResponseEntity.noContent().build();
    }


}