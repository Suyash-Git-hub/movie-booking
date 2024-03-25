package com.sapient.movieportal.movieservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.movieportal.movieservice.model.Movie;
import com.sapient.movieportal.movieservice.repository.MovieRepository;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

@Service
public class MovieServiceImpl implements MovieService
{
	@Autowired
	private MovieRepository movieRepo;

	@Override
	public List<Movie> findAllMovies()
	{
		return movieRepo.findAll();
	}

	@Override
	public Movie findMovieById(String id) throws MovieNotFoundException
	{
		validateId(id);
		Optional<Movie> movieOptional = movieRepo.findById(id);
		if(movieOptional.isEmpty())
			throw new MovieNotFoundException("No movie found for id = " + id);

		return movieOptional.get();
	}

	private void validateId(String id)
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Movie id cannot be null or empty");
	}

	@Override
	public Movie addMovie(Movie movie)
	{
		validateMovie(movie);
		return movieRepo.save(movie);
	}

	private void validateMovie(Movie movie)
	{
		if(null == movie)
			throw new IllegalArgumentException("Movie entity cannot be null");
	}

	@Override
	public Movie updateMovie(String id, Movie movie) throws MovieNotFoundException
	{
		validateId(id);
		validateMovie(movie);
		Optional<Movie> movieOptional = movieRepo.findById(id);
		if(movieOptional.isEmpty())
			throw new MovieNotFoundException("No movie found for id = " + id);

		return movieRepo.save(movie);
	}

	@Override
	public void deleteMovie(String id)
	{
		validateId(id);
		movieRepo.deleteById(id);
	}

	@Override
	public Movie addTheatreToMovie(String movieId, String theatreId) throws MovieNotFoundException
	{
		validateId(movieId);
		Optional<Movie> movieOptional = movieRepo.findById(movieId);
		if(movieOptional.isEmpty())
			throw new MovieNotFoundException("No movie found for id = " + movieId);
		Movie movie = movieOptional.get();
		movie.getTheatreIds().add(theatreId);
		return movieRepo.save(movie);
	}
}
