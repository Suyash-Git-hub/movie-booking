package com.sapient.movieportal.movieservice.service;

import java.util.List;

import com.sapient.movieportal.movieservice.model.Movie;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

public interface MovieService
{
	List<Movie> findAllMovies();

	Movie findMovieById(String id) throws MovieNotFoundException;

	Movie addMovie(Movie movie);

	Movie updateMovie(String id, Movie movie) throws MovieNotFoundException;

	void deleteMovie(String id);

	Movie addTheatreToMovie(String movieId, String theatreId) throws MovieNotFoundException;
}
