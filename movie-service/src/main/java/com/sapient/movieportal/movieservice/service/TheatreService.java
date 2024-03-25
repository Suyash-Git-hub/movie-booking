package com.sapient.movieportal.movieservice.service;

import java.util.List;

import com.sapient.movieportal.movieservice.model.Theatre;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

public interface TheatreService
{
	List<Theatre> findTheatresByMovieAndDate(String movieId, String date) throws MovieNotFoundException;
}
