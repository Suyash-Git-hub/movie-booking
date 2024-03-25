package com.sapient.movieportal.showservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.sapient.movieportal.showservice.model.Show;

public interface ShowRepository extends CrudRepository<Show, String>
{

}
