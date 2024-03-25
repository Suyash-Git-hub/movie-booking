package com.sapient.movieportal.theatreservice.util;

public class TheatreNotFoundException extends Exception
{
	private static final long serialVersionUID = -1780298355006730070L;

	public TheatreNotFoundException(String message)
	{
		super(message);
	}
}
