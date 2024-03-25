package com.sapient.movieportal.movieservice.searchservice.service;

import java.util.Set;

import com.sapient.movieportal.movieservice.searchservice.model.Movie;
import com.sapient.movieportal.movieservice.searchservice.util.MovieNotFoundException;

public interface MovieService
{
	Iterable<Movie> findAllMovies();

	Set<Movie> findAllMoviesByCity(String cityId);

	Movie findMovieById(String id) throws MovieNotFoundException;

	Iterable<Movie> findMoviesByName(String movieName);

	Movie addMovie(Movie movie);

	void removeMovieById(String id);

	Movie updateMovie(Movie movie);
}
