package com.ali.restaurant.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours {
   @Field(type = FieldType.Nested)
    private String monday;
    @Field(type = FieldType.Nested)
    private String tuesday;
    @Field(type = FieldType.Nested)
    private String wednesday;
    @Field(type = FieldType.Nested)
    private String thursday;
    @Field(type = FieldType.Nested)
    private String friday;
    @Field(type = FieldType.Nested)
    private String saturday;
    @Field(type = FieldType.Nested)
    private String sunday;

}
