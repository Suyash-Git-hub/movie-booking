package com.sapient.movieportal.movieservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sapient.movieportal.movieservice.model.Movie;
import com.sapient.movieportal.movieservice.model.Theatre;
import com.sapient.movieportal.movieservice.repository.MovieRepository;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

@Service
public class TheatreServiceImpl implements TheatreService
{
	@Autowired
	private MovieRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Theatre> findTheatresByMovieAndDate(String movieId, String date) throws MovieNotFoundException
	{
		if(null == movieId || movieId.isBlank())
			throw new IllegalArgumentException("Movie id cannot be null or blank");
		if(null == date || date.isBlank())
			throw new IllegalArgumentException("Date cannot be null or blank");
		Optional<Movie> movieOptional = repo.findById(movieId);
		if(movieOptional.isEmpty())
			throw new MovieNotFoundException("No movie available with id = " + movieId);

		Movie movie = movieOptional.get();
		String theatreIds = movie.getTheatreIds().stream().collect(Collectors.joining("&theatreIds="));
		String url = "http://localhost:8090/api/v1/theatre/date?movieId=" + movieId
				+ "&date=" + date + "&theatreIds=" + theatreIds;
		System.out.println(url);
		Theatre[] theatres = restTemplate.getForObject(url, Theatre[].class);
		return Arrays.asList(theatres);
	}

}
