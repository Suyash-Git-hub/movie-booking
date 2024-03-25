package com.sapient.movieportal.movieservice.searchservice.service;

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
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.MovieRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.TheatreRepository;

public class CityServiceImplTest
{
	@InjectMocks
	private CityServiceImpl cityService;

	@Mock
	private CityRepository cityRepo;

	@Mock
	private MovieRepository movieRepo;

	@Mock
	private TheatreRepository theatreRepo;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAll_noCityAddedToRepo_shouldReturnEmptyList() throws Exception
	{
		when(cityRepo.findAll()).thenReturn(new ArrayList<>());
		
		Iterable<City> cities = cityService.findAll();
		
		assertFalse(cities.iterator().hasNext());
	}

	@Test
	public void findAll_citiesAvailable_shouldReturnCorrectData() throws Exception
	{
		City ncr = new City("ncr", "Delhi NCR", null, null);
		when(cityRepo.findAll()).thenReturn(Arrays.asList(ncr));

		Iterable<City> citiesAvailable = cityService.findAll();

		assertTrue(citiesAvailable.iterator().hasNext());
		City city = citiesAvailable.iterator().next();
		assertEquals("ncr", city.getId());
		assertEquals("Delhi NCR", city.getName());
	}

//	@Test
//	public void getMoviesByCityName_noMoviesAvailable_shouldReturnEmptySet() throws Exception
//	{
//		City ncr = new City("ncr", "Delhi NCR", null, null);
//		when(cityRepo.findByName("Delhi NCR")).thenReturn(ncr);
//
//		Set<Movie> moviesByCityName = cityService.getMoviesByCityName("Delhi NCR");
//
//		assertTrue(moviesByCityName.isEmpty());
//	}

//	@Test
//	public void getMoviesByCityName_oneMovieAvailable_shouldReturnCorrectMovie() throws Exception
//	{
//		City ncr = new City("ncr", "Delhi NCR", Arrays.asList("SR-1"), null);
//		when(cityRepo.findByName("Delhi NCR")).thenReturn(ncr);
//		MovieBuilder builder = new MovieBuilder("SR-1", "Shawshank Redemption");
//		Optional<Movie> movie = Optional.of(builder.build());
//		when(movieRepo.findById("SR-1")).thenReturn(movie);
//
//		Set<Movie> moviesByCityName = cityService.getMoviesByCityName("Delhi NCR");
//
//		assertEquals(1, moviesByCityName.size());
//		Movie movieRunning = moviesByCityName.iterator().next();
//		assertEquals("Shawshank Redemption", movieRunning.getName());
//	}

//	@Test
//	public void getTheatresByCityName_noTheatresAvailable_shouldReturnEmptySet() throws Exception
//	{
//		City ncr = new City("ncr", "Delhi NCR", null, null);
//		when(cityRepo.findByName("Delhi NCR")).thenReturn(ncr);
//
//		Set<Theatre> theatresByCityName = cityService.getTheatresByCityName("Delhi NCR");
//
//		assertTrue(theatresByCityName.isEmpty());
//	}

//	@Test
//	public void getTheatresByCityName_oneTheatreAvailable_shouldReturnCorrectTheatre() throws Exception
//	{
//		City ncr = new City("ncr", "Delhi NCR", null, Arrays.asList("pvr-1"));
//		when(cityRepo.findByName("Delhi NCR")).thenReturn(ncr);
//		Optional<Theatre> theatre = Optional.of(new Theatre("pvr-1", "PVR Logix", null, null, 0));
//		when(theatreRepo.findById("pvr-1")).thenReturn(theatre);
//
//		Set<Theatre> theatresByCityName = cityService.getTheatresByCityName("Delhi NCR");
//
//		assertEquals(1, theatresByCityName.size());
//		Theatre availableTheatre = theatresByCityName.iterator().next();
//		assertEquals("PVR Logix", availableTheatre.getName());
//	}

	@Test
	public void addCity_callShouldBeSentToRepo() throws Exception
	{
		City ncr = new City("ncr", "Delhi NCR", null, null);

		cityService.addCity(ncr);

		verify(cityRepo).save(ncr);
	}

	@Test
	public void removeCityById_callShouldBeSentToRepo() throws Exception
	{
		cityService.removeCityById("city_id");

		verify(cityRepo).deleteById("city_id");
	}

	@Test
	public void updateCity_shouldSendCallToRepo() throws Exception
	{
		City ncr = new City("ncr", "Delhi NCR", null, null);

		cityService.updateCity(ncr);

		verify(cityRepo).save(ncr);
	}
}
