package com.sapient.movieportal.movieservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.movieportal.movieservice.model.Theatre;
import com.sapient.movieportal.movieservice.service.TheatreService;
import com.sapient.movieportal.movieservice.util.MovieNotFoundException;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/movie")
public class TheatreController
{
	@Autowired
	private TheatreService service;

	@GetMapping("/theatres")
	public List<Theatre> getTheatresByMovieAndDate(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String movieId,
			@NotNull(message = "Date cannot be null") @NotEmpty(message = "Date cannot be empty") @RequestParam String date)
			throws MovieNotFoundException
	{
		return service.findTheatresByMovieAndDate(movieId, date);
	}
}
