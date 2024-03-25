package com.sapient.movieportal.movieservice.searchservice.service;

import com.sapient.movieportal.movieservice.searchservice.model.City;

public interface CityService
{
	Iterable<City> findAll();

	City addCity(City city);

	void removeCityById(String id);

	City updateCity(City city);
}
