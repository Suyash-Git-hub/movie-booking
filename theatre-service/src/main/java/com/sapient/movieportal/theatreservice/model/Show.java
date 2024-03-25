package com.sapient.movieportal.theatreservice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Show
{
	@Id
	private String id;

	@NotNull(message = "Movie ID cannot be null")
	@NotEmpty(message = "Movie ID cannot be empty")
	private String movieId;

	@NotNull(message = "Date and time of the show is mandatory")
	private String showTime;

	private String screenName;
	private int totalSeats;
	private double price;
	private List<String> bookedSeats = new ArrayList<>();

	public Show(String id, String movieId, String showTime, String screenName, int totalSeats, double price,
			List<String> bookedSeats)
	{
		this.id = id;
		this.movieId = movieId;
		this.showTime = showTime;
		this.screenName = screenName;
		this.totalSeats = totalSeats;
		this.price = price;
		if(null != bookedSeats)
			this.bookedSeats = Collections.unmodifiableList(bookedSeats);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMovieId()
	{
		return movieId;
	}

	public void setMovieId(String movieId)
	{
		this.movieId = movieId;
	}

	public String getShowTime()
	{
		return showTime;
	}

	public void setShowTime(String showTime)
	{
		this.showTime = showTime;
	}

	public String getScreenName()
	{
		return screenName;
	}

	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}

	public int getTotalSeats()
	{
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats)
	{
		this.totalSeats = totalSeats;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public List<String> getBookedSeats()
	{
		return bookedSeats;
	}

	public void setBookedSeats(List<String> bookedSeats)
	{
		this.bookedSeats = bookedSeats;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Show other = (Show) obj;
		return Objects.equals(id, other.id);
	}
}
