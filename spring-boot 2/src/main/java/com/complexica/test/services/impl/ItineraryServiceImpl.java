package com.complexica.test.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.complexica.test.model.CityDetails;
import com.complexica.test.model.Itinerary;
import com.complexica.test.repository.ItineraryRepository;
import com.complexica.test.services.ItineraryService;

@Service
public class ItineraryServiceImpl implements ItineraryService {
	
	@Autowired
    private ItineraryRepository itineraryRepository;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Override
	public List<Itinerary> findAllItineraries() {
		
		return itineraryRepository.findAll();
	}
	
	public Itinerary findByItineraryName(String itineraryName) {
		
		return itineraryRepository.findByItineraryName(itineraryName);
	}
	
	public Itinerary findByItineraryId(Long id) {
		return itineraryRepository.findById(id).get();
	}

	
	@Override
	public Itinerary addCity(String cityName, String date, String itineraryName) {
		
		Itinerary itinerary = itineraryRepository.findByItineraryName(itineraryName);
		if (itinerary == null) {
			itinerary = new Itinerary(itineraryName);
		}
		long hours = 2;
		List<CityDetails> cityDetails = itinerary.getCityDetails();
		LocalDateTime currentTime = LocalDateTime.now();
		for (CityDetails city : cityDetails) {
			
			if (cityName.equalsIgnoreCase(city.getCityName())) {
				
				hours = Duration.between(city.getLastUpdatedTime(),currentTime).toHours();
				if (hours < 1) {
					System.out.println("not calling webservice, already in database and cache expiry time < 1hr");
					return itinerary;
				}
			}
			
		}
		System.out.println("calling webservice, cache expired or not in cache");
		CityDetails newCityDetails = getWeather(cityName, date);
		newCityDetails.setLastUpdatedTime(currentTime);
		
		newCityDetails.setItinerary(itinerary);
		itinerary.getCityDetails().add(newCityDetails);
		itineraryRepository.save(itinerary);
		
		return itinerary;
	}
	
	private CityDetails getWeather(String cityName, String fromDate) {
		
		StringBuffer urlBuffer = new StringBuffer("http://api.openweathermap.org/data/2.5/weather?appid=103218de936d332cf511787f646ca725&units=metric&q=");
    	urlBuffer.append(cityName);
    	System.out.println("Calling " + urlBuffer.toString());
    	String weatherForecast = restTemplate.getForObject(urlBuffer.toString(), String.class);
    	try {
    		return getCityDetails(weatherForecast, fromDate);
    	}catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return null;
	}
	public CityDetails getCityDetails(String weatherForecast, String fromDate) throws Exception{
    	JSONObject obj = (JSONObject) new JSONParser().parse(weatherForecast); 
    	String cityName = (String)obj.get("name");
    	JSONObject sysObj = (JSONObject)obj.get("sys");
    	String country = (String) sysObj.get("country");
    	
    	JSONObject mainObj = (JSONObject)obj.get("main");
    	Double temperature = (Double)mainObj.get("temp");
    	JSONArray wArr = (JSONArray) obj.get("weather");
    	String clouds = "not available";
    	
    	if (wArr != null && wArr.size() > 0) {
    		JSONObject wObj = (JSONObject) wArr.get(0);
    		clouds = (String)wObj.get("main");
    	}
    		
    	CityDetails cityDetails = new CityDetails(fromDate, cityName, country, temperature.toString(),clouds);
    	cityDetails.setLastUpdatedTime(LocalDateTime.now());
    	
    	return cityDetails;
    }

	@Override
	public boolean saveItinerary(List<CityDetails> cities, String name) {
		
		Itinerary itinerary = new Itinerary("iter1");
		itinerary.setCityDetails(cities);
		
		itineraryRepository.save(itinerary);
		return false;
	}

}
