package com.sapient.movieportal.theatreservice.service;

import java.util.List;

import com.sapient.movieportal.theatreservice.model.Theatre;
import com.sapient.movieportal.theatreservice.util.TheatreNotFoundException;

public interface TheatreService
{
	Theatre getTheatreById(String id) throws TheatreNotFoundException;

	List<Theatre> getTheatresByMovieAndDate(String movieId, String date, List<String> theatreIds);

	Theatre addTheatre(Theatre theatre);

	Theatre updateTheatre(String id, Theatre theatre) throws TheatreNotFoundException;

	void deleteTheatre(String id);
}
