package com.complexica.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.complexica.test.model.CityDetails;
import com.complexica.test.model.Itinerary;
import com.complexica.test.services.ItineraryService;

@Controller
public class CityDetailsController {
	
	private static final String serviceUrl = "http://api.openweathermap.org/data/2.5/weather?appid=103218de936d332cf511787f646ca725&units=metric&q=";
	
    
    @Autowired
    private ItineraryService itineraryService;
    
    @GetMapping("/")
    public String showAddCityForm(Model model) {
    	
        model.addAttribute("cityDetails", new CityDetails());
        return "index.html";
    }
    
    @PostMapping(path = "addCityToItinerary")
    public String addCityToItinerary( CityDetails cityDetails, BindingResult result, Model model, RedirectAttributes redirectAttributes)  {	
    	
    	StringBuffer urlBuffer = new StringBuffer(serviceUrl);
    	System.out.println("!!date got is " + cityDetails.getCityDate());
    	System.out.println("!!name got is " + cityDetails.getCityName());
    	System.out.println("!!itinName got is " + cityDetails.getItineraryName());
    	
    	System.out.println("Calling " + urlBuffer.toString());
    	
    	itineraryService.addCity(cityDetails.getCityName(), cityDetails.getCityDate(), cityDetails.getItineraryName());
    	redirectAttributes.addFlashAttribute("itineraryName", cityDetails.getItineraryName());
        return "redirect:list";
    }
    
    @RequestMapping("viewItinerary/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Itinerary itinerary = itineraryService.findByItineraryId(id);
        
        model.addAttribute("cities", itinerary.getCityDetails());
        model.addAttribute("cityDetails", new CityDetails());
        model.addAttribute("itineraryName", itinerary.getItineraryName());
        
        List<Itinerary> itinerariesList = itineraryService.findAllItineraries();
        model.addAttribute("itineraries", itinerariesList);
        return "index.html";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listCities(Model model){
    	String itineraryName = (String) model.asMap().get("itineraryName");
    	System.out.println("in listCities: itineraryName got is :" + itineraryName);
    	if (itineraryName == null) {
    		itineraryName = "one";
    	}
        Itinerary itinerary = itineraryService.findByItineraryName(itineraryName);
        model.addAttribute("cities", itinerary.getCityDetails());
        model.addAttribute("cityDetails", new CityDetails());
        model.addAttribute("itineraryName", itineraryName);
        
        List<Itinerary> itinerariesList = itineraryService.findAllItineraries();
        model.addAttribute("itineraries", itinerariesList);
		return "index";
        
    }


}