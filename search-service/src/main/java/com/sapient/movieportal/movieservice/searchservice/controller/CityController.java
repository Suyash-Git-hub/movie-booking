package com.sapient.movieportal.movieservice.searchservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.service.CityService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class CityController
{
	@Autowired
	private CityService cityService;

	@GetMapping("/cities")
	public Iterable<City> getAllCities()
	{
		return cityService.findAll();
	}

	@PostMapping("/city")
	public City addCity(@Valid @RequestBody City city)
	{
		return cityService.addCity(city);
	}

	@DeleteMapping("/city/{id}")
	public void removeCityById(@NotNull(message = "Id cannot be null") @NotEmpty(message = "Id cannot be empty") @PathVariable String id)
	{
		cityService.removeCityById(id);
	}

	@PutMapping("/city/{id}")
	public City updateCity(@Valid @RequestBody City city)
	{
		return cityService.updateCity(city);
	}
}
