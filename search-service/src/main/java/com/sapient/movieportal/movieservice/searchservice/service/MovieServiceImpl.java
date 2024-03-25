package com.sapient.movieportal.movieservice.searchservice.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.model.Movie;
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.MovieRepository;
import com.sapient.movieportal.movieservice.searchservice.util.MovieNotFoundException;

@Service
public class MovieServiceImpl implements MovieService
{
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private MovieRepository movieRepo;

	@Override
	public Iterable<Movie> findAllMovies()
	{
		return movieRepo.findAll();
	}

	@Override
	public Set<Movie> findAllMoviesByCity(String cityId)
	{
		if(null == cityId || cityId.isBlank())
			throw new IllegalArgumentException("City Id cannot be null or blank");
		Optional<City> optionalCity = cityRepo.findById(cityId);
		if(optionalCity.isPresent())
		{
			City city = optionalCity.get();
			return city.getMovieIds().stream().map(id -> movieRepo.findById(id)).filter(m -> m.isPresent())
					.map(m -> m.get()).collect(Collectors.toUnmodifiableSet());
		}
		return Collections.emptySet();
	}

	@Override
	public Movie findMovieById(String id) throws MovieNotFoundException
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Movie Id cannot be null or blank");
		Optional<Movie> movieById = movieRepo.findById(id);
		if(movieById.isPresent())
			return movieById.get();
		throw new MovieNotFoundException("No movie found for id = " + id);
	}

	@Override
	public Iterable<Movie> findMoviesByName(String movieName)
	{
		if(null == movieName || movieName.isBlank())
			throw new IllegalArgumentException("Movie name cannot be null or blank");
		return movieRepo.findByNameContainingIgnoreCase(movieName);
	}

	@Override
	public Movie addMovie(Movie movie)
	{
		if(null == movie)
			throw new IllegalArgumentException("Movie entity cannot be null");
		return movieRepo.save(movie);
	}

	@Override
	public void removeMovieById(String id)
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Movie Id cannot be null or blank");
		movieRepo.deleteById(id);
	}

	@Override
	public Movie updateMovie(Movie movie)
	{
		if(null == movie)
			throw new IllegalArgumentException("Movie entity cannot be null");
		return movieRepo.save(movie);
	}
}
