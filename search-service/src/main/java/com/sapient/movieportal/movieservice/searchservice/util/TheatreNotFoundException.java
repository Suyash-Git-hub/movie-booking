package com.sapient.movieportal.movieservice.searchservice.util;

public class TheatreNotFoundException extends Exception
{
	private static final long serialVersionUID = -9015381006636027468L;

	public TheatreNotFoundException(String message)
	{
		super(message);
	}
}
