package com.sapient.movieportal.movieservice.searchservice.service;

import java.util.Set;

import com.sapient.movieportal.movieservice.searchservice.model.Theatre;
import com.sapient.movieportal.movieservice.searchservice.util.TheatreNotFoundException;

public interface TheatreService
{
	Set<Theatre> findAllTheatres();

	Set<Theatre> findAllTheatresByCity(String cityId);

	Theatre findTheatreById(String theatreId) throws TheatreNotFoundException;

	Iterable<Theatre> findTheatresByName(String theatreName);

	Theatre addTheatre(Theatre theatre);

	void removeTheatreById(String theatreId);

	Theatre updateTheatre(Theatre theatre);
}
