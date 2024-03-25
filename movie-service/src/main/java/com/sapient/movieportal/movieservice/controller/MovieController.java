package com.sapient.movieportal.movieservice.controller;

import java.util.List;

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

import com.sapient.movieportal.movieservice.model.Movie;
import com.sapient.movieportal.movieservice.service.MovieService;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class MovieController
{
	@Autowired
	private MovieService service;

	@GetMapping("/movies")
	public List<Movie> getAllMovies()
	{
		return service.findAllMovies();
	}

	@GetMapping("/movie")
	public Movie getMovieById(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String id)
			throws MovieNotFoundException
	{
		return service.findMovieById(id);
	}

	@PostMapping("/movie")
	public Movie addMovie(@Valid @RequestBody Movie movie)
	{
		return service.addMovie(movie);
	}

	@PutMapping("/movie/{id}")
	public Movie updateMovie(@PathVariable String id, @Valid @RequestBody Movie movie) throws MovieNotFoundException
	{
		return service.updateMovie(id, movie);
	}
	
	@DeleteMapping("/movie/{id}")
	public void deleteMovie(@PathVariable String id)
	{
		service.deleteMovie(id);
	}

	@PutMapping("/movie/theatre")
	public Movie addTheatreToMovie(@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String movieId,
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @RequestParam String theatreId)
			throws MovieNotFoundException
	{
		return service.addTheatreToMovie(movieId, theatreId);
	}
}
