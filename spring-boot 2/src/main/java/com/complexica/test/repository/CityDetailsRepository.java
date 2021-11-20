package com.complexica.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.complexica.test.model.CityDetails;

public interface CityDetailsRepository extends JpaRepository<CityDetails, Long> {

}
