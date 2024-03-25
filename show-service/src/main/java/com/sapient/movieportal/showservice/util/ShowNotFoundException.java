package com.sapient.movieportal.showservice.util;

public class ShowNotFoundException extends Exception
{
	private static final long serialVersionUID = -3621132933163313172L;

	public ShowNotFoundException(String message)
	{
		super(message);
	}
}
