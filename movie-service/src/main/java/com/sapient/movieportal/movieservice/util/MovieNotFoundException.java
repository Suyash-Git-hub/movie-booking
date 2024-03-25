package com.sapient.movieportal.movieservice.util;

public class MovieNotFoundException extends Exception
{
	private static final long serialVersionUID = 6501764091129398796L;

	public MovieNotFoundException(String message)
	{
		super(message);
	}
}
