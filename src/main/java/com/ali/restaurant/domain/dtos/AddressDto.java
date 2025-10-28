package com.ali.restaurant.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    @NotBlank(message = "Street number is required")
    @Pattern(regexp = "^[0-9]{1,5}[a-zA-Z]?$",message = "Invalid street number format")
    private String streetNumber;
    @NotBlank(message = "streetName number is required")
    private String streetName;
    private String unit;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "state is required")
    private  String state;
    @NotBlank(message = "PostalCode is required")
    private  String PostalCode;
    @NotBlank(message = "country is required")
    private String country;
}
