package com.sapient.movieportal.movieservice.searchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sapient.movieportal.movieservice.searchservice.model.Movie;

public interface MovieRepository extends ElasticsearchRepository<Movie, String>
{
	Iterable<Movie> findByNameContainingIgnoreCase(String name);
}
