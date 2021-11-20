package com.complexica.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.complexica.test.model.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

   /* @Query("select ne from Itinerary ne")
    List<Itinerary> findAll();*/
	
	Optional<Itinerary> findById(Long id);
    
	Itinerary findByItineraryName(String itineraryName);

}