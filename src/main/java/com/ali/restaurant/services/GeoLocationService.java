package com.ali.restaurant.services;

import com.ali.restaurant.domain.GeoLocation;
import com.ali.restaurant.domain.entities.Address;
import org.springframework.stereotype.Service;

@Service
public interface GeoLocationService {
    GeoLocation getLocate(Address address);

}
