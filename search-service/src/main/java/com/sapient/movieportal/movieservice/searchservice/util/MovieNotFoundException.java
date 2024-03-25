package com.sapient.movieportal.movieservice.searchservice.util;

public class MovieNotFoundException extends Exception
{
	private static final long serialVersionUID = 3066680534719178748L;

	public MovieNotFoundException(String message)
	{
		super(message);
	}
}
