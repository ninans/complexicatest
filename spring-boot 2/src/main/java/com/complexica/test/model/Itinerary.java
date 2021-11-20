package com.complexica.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Itineraries")
public class Itinerary {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	private String itineraryName;
    
    @OneToMany(mappedBy = "itinerary", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CityDetails> cityDetails = new ArrayList<>();
    
    public Itinerary() {
    	
    }
    
    public Itinerary(String itineraryName) {
    	this.itineraryName = itineraryName;
    }
    
    public Long getId() {
		return id;
	}

	
    public void setId(Long id) {
		this.id = id;
	}


	public String getItineraryName() {
		return itineraryName;
	}


	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}


	public List<CityDetails> getCityDetails() {
		return cityDetails;
	}


	public void setCityDetails(List<CityDetails> cityDetails) {
		this.cityDetails = cityDetails;
	}


}
