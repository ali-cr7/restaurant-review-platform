package com.ali.restaurant.mappers;

import com.ali.restaurant.domain.RestaurantCreateUpdateRequest;
import com.ali.restaurant.domain.dtos.GeoPointDto;
import com.ali.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.ali.restaurant.domain.dtos.RestaurantDto;
import com.ali.restaurant.domain.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto restaurantCreateUpdateRequestDto);
    RestaurantDto toRestaurantDto (Restaurant restaurant);
    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);


}
