package com.complexica.test.services;

import java.util.List;

import com.complexica.test.model.CityDetails;
import com.complexica.test.model.Itinerary;

public interface ItineraryService {
    public List<Itinerary> findAllItineraries();
    
    public Itinerary findByItineraryName(String itineraryName);
    
    public boolean saveItinerary(List<CityDetails> itinerary, String name);

	public Itinerary addCity(String cityName, String date, String itineraryName);
	
	public Itinerary findByItineraryId(Long id);
}
