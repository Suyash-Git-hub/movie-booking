package com.sapient.movieportal.theatreservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sapient.movieportal.theatreservice.model.Theatre;

public interface TheatreRepository extends MongoRepository<Theatre, String>
{

}
