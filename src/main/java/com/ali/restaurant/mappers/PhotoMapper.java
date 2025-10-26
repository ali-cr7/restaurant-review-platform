package com.ali.restaurant.mappers;

import com.ali.restaurant.domain.dtos.PhotoDto;
import com.ali.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);
    Photo fromDto(PhotoDto photoDto);
}
