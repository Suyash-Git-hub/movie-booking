package com.sapient.movieportal.movieservice.searchservice.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.model.Theatre;
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.TheatreRepository;
import com.sapient.movieportal.movieservice.searchservice.util.TheatreNotFoundException;

@Service
public class TheatreServiceImpl implements TheatreService
{
	@Autowired
	private TheatreRepository theatreRepo;
	
	@Autowired
	private CityRepository cityRepo;

	@Override
	public Set<Theatre> findAllTheatres()
	{
		Iterable<Theatre> allTheatres = theatreRepo.findAll();
		Set<Theatre> theatres = StreamSupport.stream(allTheatres.spliterator(), false).collect(Collectors.toSet());
		return theatres;
	}

	@Override
	public Set<Theatre> findAllTheatresByCity(String cityId)
	{
		if(null == cityId || cityId.isBlank())
			throw new IllegalArgumentException("City Id cannot be null or blank");
		Optional<City> cityOptional = cityRepo.findById(cityId);
		if(cityOptional.isPresent())
		{
			City city = cityOptional.get();
			return city.getTheatreIds().stream().map(id -> theatreRepo.findById(id)).filter(t -> t.isPresent())
					.map(t -> t.get()).collect(Collectors.toUnmodifiableSet());
		}
		return Collections.emptySet();
	}

	@Override
	public Theatre findTheatreById(String theatreId) throws TheatreNotFoundException
	{
		if(null == theatreId || theatreId.isBlank())
			throw new IllegalArgumentException("Theatre Id cannot be null or blank");
		Optional<Theatre> theatreById = theatreRepo.findById(theatreId);
		if(theatreById.isPresent())
			return theatreById.get();
		throw new TheatreNotFoundException("No theatre found for id = " + theatreId);
	}

	@Override
	public Iterable<Theatre> findTheatresByName(String theatreName)
	{
		if(null == theatreName || theatreName.isBlank())
			throw new IllegalArgumentException("Theatre name cannot be null or blank");
		return theatreRepo.findByNameContainingIgnoreCase(theatreName);
	}

	@Override
	public Theatre addTheatre(Theatre theatre)
	{
		if(null == theatre)
			throw new IllegalArgumentException("Theatre entity cannot be null");
		return theatreRepo.save(theatre);
	}

	@Override
	public void removeTheatreById(String id)
	{
		if(null == id || id.isBlank())
			throw new IllegalArgumentException("Theatre Id cannot be null or blank");
		theatreRepo.deleteById(id);
	}

	@Override
	public Theatre updateTheatre(Theatre theatre)
	{
		if(null == theatre)
			throw new IllegalArgumentException("Theatre entity cannot be null");
		return theatreRepo.save(theatre);
	}
}
