package com.sapient.movieportal.movieservice.searchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sapient.movieportal.movieservice.searchservice.model.Theatre;

public interface TheatreRepository extends ElasticsearchRepository<Theatre, String>
{
	Iterable<Theatre> findByNameContainingIgnoreCase(String theatreName);
}
