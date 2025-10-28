package com.ali.restaurant.services;

import com.ali.restaurant.domain.GeoLocation;
import com.ali.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation getLocate(Address address);

}
