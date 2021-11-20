package com.complexica.test.model;


import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CityDetails")
public class CityDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cityDate;
    private String cityName;
    private String countryCode;
    private String temperature;
    private String clouds;
    private LocalDateTime lastUpdatedTime;
    private String itineraryName;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;
    
    public CityDetails() {
    	
    }
    
    public CityDetails(String cityDate, String cityName, String countryCode, String temperature, String clouds) {
    	this.cityDate = cityDate;
    	this.cityName = cityName;
    	this.countryCode = countryCode;
    	this.temperature = temperature;
    	this.clouds = clouds;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityDate() {
		return cityDate;
	}

	public void setCityDate(String cityDate) {
		this.cityDate = cityDate;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getClouds() {
		return clouds;
	}

	public void setClouds(String clouds) {
		this.clouds = clouds;
	}

	public LocalDateTime getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}	

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityDetails that = (CityDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityName);
    }

    @Override
    public String toString() {
        return "NameEntity{" +
                "id=" + id +
                ", name='" + cityName + '\'' +
                '}';
    }
}