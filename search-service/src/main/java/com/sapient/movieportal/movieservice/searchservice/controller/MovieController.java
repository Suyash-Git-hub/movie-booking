package com.sapient.movieportal.movieservice.searchservice.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.movieportal.movieservice.searchservice.model.Movie;
import com.sapient.movieportal.movieservice.searchservice.service.MovieService;
import com.sapient.movieportal.movieservice.searchservice.util.MovieNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class MovieController
{
	@Autowired
	private MovieService movieService;

	@GetMapping("/movies")
	public Iterable<Movie> getAllMovies()
	{
		return movieService.findAllMovies();
	}

	@GetMapping("/movies/city")
	public Set<Movie> getAllMoviesByCity(
			@NotNull(message = "City Id cannot be null") @NotEmpty(message = "City Id cannot be empty") @RequestParam String cityId)
	{
		return movieService.findAllMoviesByCity(cityId);
	}

	@GetMapping("/movie")
	public Movie getMovieById(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String movieId)
			throws MovieNotFoundException
	{
		return movieService.findMovieById(movieId);
	}

	@GetMapping("/movies/search")
	public Iterable<Movie> searchMovieByName(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String movieName)
	{
		return movieService.findMoviesByName(movieName);
	}

	@PostMapping("/movie")
	public Movie addMovie(@Valid @RequestBody Movie movie)
	{
		return movieService.addMovie(movie);
	}

	@DeleteMapping("/movie/{id}")
	public void deleteMovieById(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @PathVariable String id)
	{
		movieService.removeMovieById(id);
	}

	@PutMapping("/movie")
	public Movie updateMovie(@Valid @RequestBody Movie movie)
	{
		return movieService.updateMovie(movie);
	}
}
