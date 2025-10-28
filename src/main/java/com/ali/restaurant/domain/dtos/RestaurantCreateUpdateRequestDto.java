package com.ali.restaurant.domain.dtos;

import com.ali.restaurant.domain.entities.Address;
import com.ali.restaurant.domain.entities.OperatingHours;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RestaurantCreateUpdateRequestDto {
    @NotBlank(message = "Restaurant name is required")
    private String name;
    @NotBlank(message = "Cuisine type is required")
    private String cuisineType;
    @NotBlank(message = "Contact information is required")
    private String contactInformation;
    @Valid
    private AddressDto address;
    @Valid
    private OperatingHoursDto operatingHours;
    @Size(min = 1,message = "At least one photo ID is required ")
    private List<String> photoIds;
}
