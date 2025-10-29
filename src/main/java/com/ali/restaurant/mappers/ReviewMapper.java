package com.ali.restaurant.mappers;

import com.ali.restaurant.domain.ReviewCreateUpdateRequest;
import com.ali.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import com.ali.restaurant.domain.dtos.ReviewDto;
import com.ali.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);
    ReviewDto toDto(Review review);
}
