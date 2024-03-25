package com.sapient.movieportal.movieservice.controller;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MovieControllerTest
{
	@InjectMocks
	private MovieController controller;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

}
