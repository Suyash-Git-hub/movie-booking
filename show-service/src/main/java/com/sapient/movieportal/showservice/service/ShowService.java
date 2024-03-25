package com.sapient.movieportal.showservice.service;

import java.util.List;

import com.sapient.movieportal.showservice.model.Show;
import com.sapient.movieportal.showservice.util.ShowNotFoundException;

public interface ShowService
{
	Show findShowById(String id) throws ShowNotFoundException;

	List<Show> findShowsByIdAndDate(String date, List<String> ids);

	Show addShow(Show show);

	Show updateShow(String id, Show show) throws ShowNotFoundException;

	void deleteShow(String id);

	Iterable<Show> findAllShows(List<String> ids);
}
