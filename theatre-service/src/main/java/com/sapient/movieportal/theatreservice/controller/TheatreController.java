package com.sapient.movieportal.theatreservice.controller;

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

import com.sapient.movieportal.theatreservice.model.Theatre;
import com.sapient.movieportal.theatreservice.service.TheatreService;
import com.sapient.movieportal.theatreservice.util.TheatreNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/theatre")
public class TheatreController
{
	@Autowired
	private TheatreService service;

	@GetMapping
	public Theatre getTheatreById(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @RequestParam String id)
			throws TheatreNotFoundException
	{
		return service.getTheatreById(id);
	}

	@GetMapping("/date")
	public List<Theatre> getTheatresByMovieAndDate(
			@NotNull(message = "Movie Id cannot be null") @NotEmpty(message = "Movie Id cannot be empty") @RequestParam String movieId,
			@NotNull(message = "Date cannot be null") @NotEmpty(message = "Date cannot be empty") @RequestParam String date,
			@NotNull(message = "Theatre id list cannot be null") @RequestParam List<String> theatreIds)
	{
		return service.getTheatresByMovieAndDate(movieId, date, theatreIds);
	}

	@PostMapping
	public Theatre addTheatre(@Valid @RequestBody Theatre theatre)
	{
		return service.addTheatre(theatre);
	}

	@PutMapping("/{id}")
	public Theatre updateTheatre(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @PathVariable String id,
			@Valid @RequestBody Theatre theatre) throws TheatreNotFoundException
	{
		return service.updateTheatre(id, theatre);
	}
	
	@DeleteMapping("/{id}")
	public void removeTheatre(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @PathVariable String id)
	{
		service.deleteTheatre(id);
	}
}
