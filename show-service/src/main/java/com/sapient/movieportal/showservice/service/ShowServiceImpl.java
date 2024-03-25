package com.sapient.movieportal.showservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.movieportal.showservice.model.Show;
import com.sapient.movieportal.showservice.repository.ShowRepository;
import com.sapient.movieportal.showservice.util.ShowNotFoundException;

@Service
public class ShowServiceImpl implements ShowService
{
	@Autowired
	private ShowRepository repo;

	@Override
	public Show findShowById(String id) throws ShowNotFoundException
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Show Id cannot be null or blank");
		return null;
	}

	@Override
	public Show addShow(Show show)
	{
		return repo.save(show);
	}

	@Override
	public Show updateShow(String id, Show show) throws ShowNotFoundException
	{
		Optional<Show> showOptional = repo.findById(id);
		if(showOptional.isEmpty())
			throw new ShowNotFoundException("No show found with id = " + id);
		show.setId(id);
		return repo.save(show);
	}

	@Override
	public void deleteShow(String id)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<Show> findShowsByIdAndDate(String date, List<String> ids)
	{
		Iterable<Show> allShows = repo.findAllById(ids);
		Iterator<Show> iterator = allShows.iterator();
		List<Show> filteredShows = new ArrayList<>();
		while (iterator.hasNext())
		{
			Show show = iterator.next();
			String showDateTime = show.getShowTime();
			if(showDateTime.startsWith(date))
				filteredShows.add(show);
		}
		return filteredShows;
	}

	@Override
	public Iterable<Show> findAllShows(List<String> ids)
	{
		return repo.findAllById(ids);
	}

}
