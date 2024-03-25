package com.sapient.movieportal.movieservice.searchservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.model.Movie;
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.MovieRepository;
import com.sapient.movieportal.movieservice.searchservice.util.MovieNotFoundException;

public class MovieServiceImplTest
{
	@InjectMocks
	private MovieServiceImpl movieService;

	@Mock
	private CityRepository cityRepo;
	@Mock
	private MovieRepository movieRepo;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAllMovies_noMoviesAvailable_shouldReturnEmptySet() throws Exception
	{
		when(movieRepo.findAll()).thenReturn(new ArrayList<>());

		Iterable<Movie> allMovies = movieService.findAllMovies();

		assertFalse(allMovies.iterator().hasNext());
	}

	@Test
	public void findAllMovies_moviesAvailable_shouldReturnCorrectSet() throws Exception
	{
		List<Movie> movies = new ArrayList<>();
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		movies.add(movie1);
		Movie movie2 = new Movie("movie-2", "The Godfather", null, 0, 0, null, 0, null, null);
		movies.add(movie2);
		when(movieRepo.findAll()).thenReturn(movies);

		Iterable<Movie> allMovies = movieService.findAllMovies();

		Iterator<Movie> iterator = allMovies.iterator();
		List<Movie> movieList = new ArrayList<>();
		while(iterator.hasNext())
			movieList.add(iterator.next());
		assertEquals(2, movieList.size());
		assertTrue(movieList.contains(movie1));
		assertTrue(movieList.contains(movie2));
	}

	@Test
	public void findAllMoviesByCity_cityIdNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findAllMoviesByCity(null);
		});

		assertEquals("City Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findAllMoviesByCity_cityIdEmpty_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findAllMoviesByCity("");
		});

		assertEquals("City Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findAllMoviesByCity_cityNotAvailable_shouldReturnEmptyIterator() throws Exception
	{
		when(cityRepo.findById("city-1")).thenReturn(Optional.empty());
		
		Set<Movie> allMoviesByCity = movieService.findAllMoviesByCity("city-1");
		
		assertFalse(allMoviesByCity.iterator().hasNext());
	}

	@Test
	public void findAllMoviesByCity_cityAvailableButNoMoviesAvailable_shouldReturnEmptyIterator() throws Exception
	{
		City ncr = new City("city-1", "Delhi NCR", null, null);
		when(cityRepo.findById("city-1")).thenReturn(Optional.of(ncr));
		
		Set<Movie> allMoviesByCity = movieService.findAllMoviesByCity("city-1");
		
		assertFalse(allMoviesByCity.iterator().hasNext());
	}

	@Test
	public void findAllMoviesByCity_moviesAvailableForCity_shouldReturnCorrectList() throws Exception
	{
		City ncr = new City("city-1", "Delhi NCR", Arrays.asList("movie-1"), null);
		when(cityRepo.findById("city-1")).thenReturn(Optional.of(ncr));
		Movie movie = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		when(movieRepo.findById("movie-1")).thenReturn(Optional.of(movie));

		Set<Movie> allMoviesByCity = movieService.findAllMoviesByCity("city-1");

		Movie movie1 = allMoviesByCity.iterator().next();
		assertEquals("The Shawshank Redemption", movie1.getName());
	}

	@Test
	public void findMovieById_idNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findMovieById(null);
		});

		assertEquals("Movie Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findMovieById_idBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findMovieById("");
		});

		assertEquals("Movie Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findMovieById_movieDoesNotExist_shouldThrowException() throws Exception
	{
		when(movieRepo.findById("movie-1")).thenReturn(Optional.empty());
		
		Exception ex = assertThrows(MovieNotFoundException.class, () -> {
			movieService.findMovieById("movie-1");
		});

		assertEquals("No movie found for id = movie-1", ex.getMessage());
	}

	@Test
	public void findMovieById_movieExists_shouldReturnMovie() throws Exception
	{
		Movie movie = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		when(movieRepo.findById("movie-1")).thenReturn(Optional.of(movie));
		
		Movie movieById = movieService.findMovieById("movie-1");
		
		assertEquals("The Shawshank Redemption", movieById.getName());
	}

	@Test
	public void findMoviesByName_nameNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findMoviesByName(null);
		});

		assertEquals("Movie name cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findMoviesByName_nameEmpty_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.findMoviesByName("");
		});

		assertEquals("Movie name cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findMoviesByName_noMovieAvailableWithGivenName_shouldReturnEmptyPage() throws Exception
	{
		when(movieRepo.findByNameContainingIgnoreCase(eq("movie"))).thenReturn(new ArrayList<>());
		
		Iterable<Movie> moviesFound = movieService.findMoviesByName("movie");

		assertFalse(moviesFound.iterator().hasNext());
	}

	@Test
	public void findMoviesByName_moviesAvailableWithGivenName_shouldReturnCorrectPage() throws Exception
	{
		List<Movie> movies = new ArrayList<>();
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		movies.add(movie1);
		Movie movie2 = new Movie("movie-2", "The Godfather", null, 0, 0, null, 0, null, null);
		movies.add(movie2);
		when(movieRepo.findByNameContainingIgnoreCase(eq("the"))).thenReturn(movies);
		
		Iterable<Movie> moviesFound = movieService.findMoviesByName("the");
		
		Iterator<Movie> iterator = moviesFound.iterator();
		List<Movie> movieList = new ArrayList<>();
		while(iterator.hasNext())
			movieList.add(iterator.next());
		assertEquals(2, movieList.size());
		assertTrue(movieList.contains(movie1));
		assertTrue(movieList.contains(movie2));
	}

	@Test
	public void addMovie_movieNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.addMovie(null);
		});

		assertEquals("Movie entity cannot be null", ex.getMessage());
	}

	@Test
	public void addMovie_movieValid_callShouldBeSentToRepo() throws Exception
	{
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);

		movieService.addMovie(movie1);

		ArgumentCaptor<Movie> movieArg = ArgumentCaptor.forClass(Movie.class);
		verify(movieRepo).save(movieArg.capture());
		Movie movie = movieArg.getValue();
		assertEquals("The Shawshank Redemption", movie.getName());
	}

	@Test
	public void removeMovieById_idNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.removeMovieById(null);
		});

		assertEquals("Movie Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void removeMovieById_idBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.removeMovieById("");
		});

		assertEquals("Movie Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void removeMovieById_idAvailable_callShouldBeSentToRepo() throws Exception
	{
		movieService.removeMovieById("movie-1");

		ArgumentCaptor<String> movieArg = ArgumentCaptor.forClass(String.class);
		verify(movieRepo).deleteById(movieArg.capture());
		String id = movieArg.getValue();
		assertEquals("movie-1", id);
	}

	@Test
	public void updateMovie_movieNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			movieService.updateMovie(null);
		});

		assertEquals("Movie entity cannot be null", ex.getMessage());
	}

	@Test
	public void updateMovie_movieValid_callShouldBeSentToRepo() throws Exception
	{
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);

		movieService.updateMovie(movie1);

		ArgumentCaptor<Movie> movieArg = ArgumentCaptor.forClass(Movie.class);
		verify(movieRepo).save(movieArg.capture());
		Movie movie = movieArg.getValue();
		assertEquals("The Shawshank Redemption", movie.getName());
	}
}
