package com.sapient.movieportal.theatreservice.service;

import java.util.ArrayList;
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
public class TheatreServiceImpl implements TheatreService
{
	@Autowired
	private TheatreRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Theatre getTheatreById(String id) throws TheatreNotFoundException
	{
		Optional<Theatre> theatreOptional = repo.findById(id);
		if(theatreOptional.isEmpty())
			throw new TheatreNotFoundException("No theatre found for id = " + id);

		return theatreOptional.get();
	}

	@Override
	public Theatre addTheatre(Theatre theatre)
	{
		if(null == theatre)
			throw new IllegalArgumentException("Theatre entity cannot be null");
		return repo.save(theatre);
	}

	@Override
	public Theatre updateTheatre(String id, Theatre theatre) throws TheatreNotFoundException
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Theatre id cannot be null or blank");
		if(null == theatre)
			throw new IllegalArgumentException("Theatre entity cannot be null");
		Optional<Theatre> theatreOptional = repo.findById(id);
		if(theatreOptional.isEmpty())
			throw new TheatreNotFoundException("No theatre found for id = " + id);
		Theatre theatreInRepo = theatreOptional.get();
		return repo.save(theatreInRepo);
	}

	@Override
	public void deleteTheatre(String id)
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Theatre id cannot be null or blank");
		repo.deleteById(id);
	}

	@Override
	public List<Theatre> getTheatresByMovieAndDate(String movieId, String date, List<String> theatreIds)
	{
		List<Theatre> theatresWithShows = new ArrayList<>();
		for (String id : theatreIds)
		{
			Optional<Theatre> theatreOptional = repo.findById(id);
			if(theatreOptional.isPresent())
			{
				Theatre theatre = theatreOptional.get();
				List<String> theatreShows = new ArrayList<>();
				theatreShows = theatre.getShowIds();
				String collectedShowIds = theatreShows.stream().collect(Collectors.joining("&ids="));
				String url = "http://localhost:9000/api/v1/show/date?date=" + date + "&ids=" + collectedShowIds;
				Show[] filteredShows = restTemplate.getForObject(url, Show[].class);
				theatre.getShowIds().clear();
				for (Show show : filteredShows)
					theatre.getShowIds().add(show.getId());
				theatresWithShows.add(theatre);
			}
		}
		return theatresWithShows;
	}
}
