package com.sapient.movieportal.movieservice.searchservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sapient.movieportal.movieservice.searchservice.model.Movie;
import com.sapient.movieportal.movieservice.searchservice.service.MovieServiceImpl;
import com.sapient.movieportal.movieservice.searchservice.util.MovieNotFoundException;

public class MovieControllerTest
{
	@InjectMocks
	private MovieController controller;

	@Mock
	private MovieServiceImpl movieService;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllMovies_noMoviesAvailable_shouldReturnEmptySet() throws Exception
	{
		when(movieService.findAllMovies()).thenReturn(new ArrayList<>());
		
		Iterable<Movie> allMovies = controller.getAllMovies();
		
		assertFalse(allMovies.iterator().hasNext());
	}

	@Test
	public void getAllMovies_moviesAvailable_shouldReturnCorrectSet() throws Exception
	{
		Movie movie = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		Page<Movie> movies = new PageImpl<>(Arrays.asList(movie));
		when(movieService.findAllMovies()).thenReturn(movies);

		Iterable<Movie> allMovies = controller.getAllMovies();

		assertEquals("The Shawshank Redemption", allMovies.iterator().next().getName());
	}

	@Test
	public void getAllMoviesByCity_noMoviesAvailable_shouldReturnEmptySet() throws Exception
	{
		when(movieService.findAllMoviesByCity("city-1")).thenReturn(new HashSet<>());
		
		Set<Movie> allMovies = controller.getAllMoviesByCity("city-1");
		
		assertTrue(allMovies.isEmpty());
	}

	@Test
	public void getAllMoviesByCity_moviesAvailable_shouldReturnCorrectSet() throws Exception
	{
		Movie movie = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		Set<Movie> movies = new HashSet<>(Arrays.asList(movie));
		when(movieService.findAllMoviesByCity("city-1")).thenReturn(movies);
		
		Set<Movie> allMovies = controller.getAllMoviesByCity("city-1");
		
		assertEquals(1, allMovies.size());
		assertEquals("The Shawshank Redemption", allMovies.iterator().next().getName());
	}

	@Test
	public void getMovieById_movieDoesNotExist_shouldThrowException() throws Exception
	{
		when(movieService.findMovieById("movie-1")).thenThrow(new MovieNotFoundException("No movie found for id = movie-1"));
		
		Exception ex = assertThrows(MovieNotFoundException.class, () -> {
			controller.getMovieById("movie-1");
		});

		assertEquals("No movie found for id = movie-1", ex.getMessage());
	}

	@Test
	public void getMovieById_movieExists_shouldReturnCorrectMovie() throws Exception
	{
		Movie movie = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		when(movieService.findMovieById("movie-1")).thenReturn(movie);
		
		Movie movieById = controller.getMovieById("movie-1");
		
		assertEquals("The Shawshank Redemption", movieById.getName());
	}

	@Test
	public void searchMovieByName_noMovieFound_shouldReturnEmptyPage() throws Exception
	{
		when(movieService.findMoviesByName("the")).thenReturn(Page.empty());
		
		Iterable<Movie> result = controller.searchMovieByName("the");
		
		assertFalse(result.iterator().hasNext());
	}

	@Test
	public void searchMovieByName_multipleMoviesFound_shouldReturnCorrectPage() throws Exception
	{
		List<Movie> movies = new ArrayList<>();
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);
		movies.add(movie1);
		Movie movie2 = new Movie("movie-2", "The Godfather", null, 0, 0, null, 0, null, null);
		movies.add(movie2);
		Page<Movie> moviePages = new PageImpl<Movie>(movies);
		when(movieService.findMoviesByName("the")).thenReturn(moviePages);
		
		Iterable<Movie> result = controller.searchMovieByName("the");
		
		Iterator<Movie> iterator = result.iterator();
		List<Movie> movieList = new ArrayList<>();
		while(iterator.hasNext())
			movieList.add(iterator.next());
		assertEquals(2, movieList.size());
		assertTrue(movieList.contains(movie1));
		assertTrue(movieList.contains(movie2));
	}

	@Test
	public void addMovie_movieValid_callShouldBeSentToTheService() throws Exception
	{
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);

		controller.addMovie(movie1);

		verify(movieService).addMovie(movie1);
	}

	@Test
	public void deleteMovieById_movieIdValid_callShouldBeSentToTheService() throws Exception
	{
		controller.deleteMovieById("movie-1");

		verify(movieService).removeMovieById("movie-1");
	}

	@Test
	public void updateMovie_movieValid_callShouldBeSentToTheService() throws Exception
	{
		Movie movie1 = new Movie("movie-1", "The Shawshank Redemption", null, 0, 0, null, 0, null, null);

		controller.updateMovie(movie1);

		verify(movieService).updateMovie(movie1);
	}
}