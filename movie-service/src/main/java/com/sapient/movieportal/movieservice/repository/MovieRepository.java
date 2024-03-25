package com.sapient.movieportal.movieservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sapient.movieportal.movieservice.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String>
{

}
