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

import com.sapient.movieportal.movieservice.searchservice.model.Theatre;
import com.sapient.movieportal.movieservice.searchservice.service.TheatreService;
import com.sapient.movieportal.movieservice.searchservice.util.TheatreNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class TheatreController
{
	@Autowired
	private TheatreService theatreService;

	@GetMapping("/theatres")
	public Set<Theatre> getAllTheatres()
	{
		return theatreService.findAllTheatres();
	}

	@GetMapping("/theatres/city")
	public Set<Theatre> getAllTheatresByCity(
			@NotNull(message = "City Id cannot be null") @NotEmpty(message = "City Id cannot be empty") @RequestParam String cityId)
	{
		return theatreService.findAllTheatresByCity(cityId);
	}

	@GetMapping("/theatre")
	public Theatre getTheatreById(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @RequestParam String theatreId)
			throws TheatreNotFoundException
	{
		return theatreService.findTheatreById(theatreId);
	}

	@GetMapping("/theatres/search")
	public Iterable<Theatre> searchTheatresByName(
			@NotNull(message = "Theatre name cannot be null") @NotEmpty(message = "Theatre name cannot be empty") @RequestParam String theatreName)
	{
		return theatreService.findTheatresByName(theatreName);
	}

	@PostMapping("/theatre")
	public Theatre addTheatre(@Valid @RequestBody Theatre theatre)
	{
		return theatreService.addTheatre(theatre);
	}

	@DeleteMapping("/theatre/{id}")
	public void deleteTheatreById(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @PathVariable String id)
	{
		theatreService.removeTheatreById(id);
	}

	@PutMapping("/theatre")
	public Theatre updateTheatre(@Valid @RequestBody Theatre theatre)
	{
		return theatreService.updateTheatre(theatre);
	}
}
