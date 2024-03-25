package com.sapient.movieportal.theatreservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sapient.movieportal.theatreservice.model.Show;
import com.sapient.movieportal.theatreservice.model.Theatre;
import com.sapient.movieportal.theatreservice.repository.TheatreRepository;
import com.sapient.movieportal.theatreservice.util.TheatreNotFoundException;

@Service
public class ShowServiceImpl implements ShowService
{
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TheatreRepository repo;

	@Override
	public Theatre addShowToTheatre(String theatreId, Show show) throws TheatreNotFoundException
	{
		Optional<Theatre> theatreOptional = validateParams(theatreId, show);

		Show createdShow = restTemplate.postForObject("http://localhost:9000/api/v1/show", show, Show.class);
		Theatre theatre = theatreOptional.get();
		theatre.getShowIds().add(createdShow.getId());
		return repo.save(theatre);
	}

	private Optional<Theatre> validateParams(String theatreId, Show show) throws TheatreNotFoundException
	{
		if(null == theatreId || theatreId.isBlank())
			throw new IllegalArgumentException("Theatre id cannot be null or blank");
		if(null == show)
			throw new IllegalArgumentException("Show entity cannot be null");
		Optional<Theatre> theatreOptional = repo.findById(theatreId);
		if(theatreOptional.isEmpty())
			throw new TheatreNotFoundException("No theatre found for id = " + theatreId);
		return theatreOptional;
	}

	@Override
	public Theatre removeShowsFromTheatre(String theatreId, List<String> showIds) throws TheatreNotFoundException
	{
		if(null == theatreId || theatreId.isBlank())
			throw new IllegalArgumentException("Theatre id cannot be null or blank");
		if(null == showIds)
			throw new IllegalArgumentException("Show entity cannot be null");
		Optional<Theatre> theatreOptional = repo.findById(theatreId);
		if(theatreOptional.isEmpty())
			throw new TheatreNotFoundException("No theatre found for id = " + theatreId);
		showIds.stream().forEach(t -> restTemplate.delete("http://localhost:9000/api/v1/show/" + t));
		Theatre theatre = theatreOptional.get();
		theatre.getShowIds().removeAll(showIds);
		return repo.save(theatre);
	}

	@Override
	public void updateShowForTheatre(String id, String showId, Show show) throws TheatreNotFoundException
	{
		if(null == showId || showId.isBlank())
			throw new IllegalArgumentException("Show id cannot be null or blank");

		restTemplate.put("http://localhost:9000/api/v1/show/" + showId, show);
	}

	@Override
	public List<Show> findAllShowsForTheatre(String theatreId) throws TheatreNotFoundException
	{
		if(null == theatreId || theatreId.isBlank())
			throw new IllegalArgumentException("Theatre id cannot be null or blank");
		Optional<Theatre> theatreOptional = repo.findById(theatreId);
		if(theatreOptional.isEmpty())
			throw new TheatreNotFoundException("No theatre found for id = " + theatreId);
		Theatre theatre = theatreOptional.get();
		String collectedShowIds = theatre.getShowIds().stream().collect(Collectors.joining("&ids="));
		String url = "http://localhost:9000/api/v1/show/shows?ids=" + collectedShowIds;
		Show[] shows = restTemplate.getForObject(url, Show[].class);
		return Arrays.asList(shows);
	}

	@Override
	public Show findShowById(String id)
	{
		return restTemplate.getForObject("http://localhost:9000/api/v1/show?id=" + id, Show.class);
	}
}
