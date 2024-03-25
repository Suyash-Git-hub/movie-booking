package com.sapient.movieportal.theatreservice.service;

import java.util.List;

import com.sapient.movieportal.theatreservice.model.Show;
import com.sapient.movieportal.theatreservice.model.Theatre;
import com.sapient.movieportal.theatreservice.util.TheatreNotFoundException;

public interface ShowService
{
	Theatre addShowToTheatre(String theatreId, Show show) throws TheatreNotFoundException;

	Theatre removeShowsFromTheatre(String theatreId, List<String> showIds) throws TheatreNotFoundException;

	void updateShowForTheatre(String id, String showId, Show show) throws TheatreNotFoundException;

	List<Show> findAllShowsForTheatre(String theatreId) throws TheatreNotFoundException;

	Show findShowById(String id);
}
