package com.sapient.movieportal.movieservice.searchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;

@Service
public class CityServiceImpl implements CityService
{
	@Autowired
	private CityRepository cityRepo;

	@Override
	public Iterable<City> findAll()
	{
		return cityRepo.findAll();
	}

//	@Override
//	public Set<Theatre> getTheatresByCityName(String cityName)
//	{
//		City cityByName = cityRepo.findByName(cityName);
//		return cityByName.getTheatreIds().stream().map(id -> theatreRepo.findById(id)).filter(m -> m.isPresent())
//				.map(m -> m.get()).collect(Collectors.toUnmodifiableSet());
//	}

	@Override
	public City addCity(City city)
	{
		return cityRepo.save(city);
	}

	@Override
	public void removeCityById(String id)
	{
		cityRepo.deleteById(id);
	}

	@Override
	public City updateCity(City city)
	{
		return cityRepo.save(city);
	}
}
