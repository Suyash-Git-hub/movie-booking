package com.sapient.movieportal.movieservice.searchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sapient.movieportal.movieservice.searchservice.model.City;

public interface CityRepository extends ElasticsearchRepository<City, String>
{
	City findByName(String name);
}
