package com.sapient.movieportal.showservice.controller;

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

import com.sapient.movieportal.showservice.model.Show;
import com.sapient.movieportal.showservice.service.ShowService;
import com.sapient.movieportal.showservice.util.ShowNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/show")
public class ShowController
{
	@Autowired
	private ShowService service;

	@GetMapping
	public Show getShowById(
			@NotNull(message = "Show Id cannot be null") @NotEmpty(message = "Show Id cannot be empty") @RequestParam String id)
			throws ShowNotFoundException
	{
		return service.findShowById(id);
	}

	@GetMapping("/shows")
	public Iterable<Show> getShowsByIds(
			@NotNull(message = "Show Ids list cannot be null") @RequestParam List<String> ids)
	{
		return service.findAllShows(ids);
	}

	@GetMapping("/date")
	public List<Show> getShowsByIdAndDate(
			@NotNull(message = "Show date cannot be null") @NotEmpty(message = "Show date cannot be empty") @RequestParam String date,
			@NotNull(message = "Show Ids list cannot be null") @RequestParam List<String> ids)
	{
		return service.findShowsByIdAndDate(date, ids);
	}

	@PostMapping
	public Show addShow(@Valid @RequestBody Show show)
	{
		return service.addShow(show);
	}

	@PutMapping("/{id}")
	public Show updateShow(@PathVariable String id, @Valid @RequestBody Show show) throws ShowNotFoundException
	{
		return service.updateShow(id, show);
	}

	@DeleteMapping("/{id}")
	public void deleteShow(@PathVariable String id)
	{
		service.deleteShow(id);
	}
}
