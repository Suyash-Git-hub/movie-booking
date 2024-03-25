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

import com.sapient.movieportal.theatreservice.model.Show;
import com.sapient.movieportal.theatreservice.model.Theatre;
import com.sapient.movieportal.theatreservice.service.ShowService;
import com.sapient.movieportal.theatreservice.util.TheatreNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/theatre/show")
public class ShowController
{
	@Autowired
	private ShowService service;

	@GetMapping
	public Show getShowById(
			@NotNull(message = "Show Id cannot be null") @NotEmpty(message = "Show Id cannot be empty") @RequestParam String id)
	{
		return service.findShowById(id);
	}

	@GetMapping("/shows")
	public List<Show> getAllShowsForTheatre(
			@NotNull(message = "Theatre Id cannot be null") @NotEmpty(message = "Theatre Id cannot be empty") @RequestParam String theatreId)
			throws TheatreNotFoundException
	{
		return service.findAllShowsForTheatre(theatreId);
	}

	@PostMapping
	public Theatre addShowToTheatre(@PathVariable String id, @RequestBody Show show) throws TheatreNotFoundException
	{
		return service.addShowToTheatre(id, show);
	}

	@DeleteMapping("{id}")
	public Theatre removeShowsFromTheatre(@PathVariable String id, @RequestBody List<String> showIds)
			throws TheatreNotFoundException
	{
		return service.removeShowsFromTheatre(id, showIds);
	}

	@PutMapping("{id}")
	public void updateShowForTheatre(@PathVariable String id,
			@NotNull(message = "Show Id cannot be null") @NotEmpty(message = "Show Id cannot be empty") @RequestParam String showId,
			@Valid @RequestBody Show show) throws TheatreNotFoundException
	{
		service.updateShowForTheatre(id, showId, show);
	}
}
