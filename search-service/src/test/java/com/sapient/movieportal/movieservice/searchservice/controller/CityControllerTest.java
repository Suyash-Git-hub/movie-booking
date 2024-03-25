package com.sapient.movieportal.movieservice.searchservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.service.CityService;

public class CityControllerTest
{
	@InjectMocks
	private CityController controller;

	@Mock
	private CityService cityService;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAll_noCityAddedToRepo_shouldReturnEmptyList() throws Exception
	{
		when(cityService.findAll()).thenReturn(new ArrayList<>());
		
		Iterable<City> cities = controller.getAllCities();
		
		assertFalse(cities.iterator().hasNext());
	}

	@Test
	public void findAll_citiesAvailable_shouldReturnCorrectData() throws Exception
	{
		City ncr = new City("ncr", "Delhi NCR", null, null);
		when(cityService.findAll()).thenReturn(Arrays.asList(ncr));

		Iterable<City> citiesAvailable = controller.getAllCities();

		assertTrue(citiesAvailable.iterator().hasNext());
		City city = citiesAvailable.iterator().next();
		assertEquals("ncr", city.getId());
		assertEquals("Delhi NCR", city.getName());
	}

	@Test
	public void addCity_requestBodyValid_callShouldBeSentToTheService() throws Exception
	{
		City ncr = new City("ncr", "Delhi NCR", null, null);

		controller.addCity(ncr);

		verify(cityService).addCity(ncr);
	}

	@Test
	public void removeCityById_callShouldBeSentToTheService() throws Exception
	{
		controller.removeCityById("city_id");

		verify(cityService).removeCityById("city_id");
	}

	@Test
	public void updateCity_shouldSendCallToService() throws Exception
	{
		String id = "ncr";
		City ncr = new City(id, "Delhi NCR", null, null);

		controller.updateCity(ncr);

		verify(cityService).updateCity(ncr);
	}
}
