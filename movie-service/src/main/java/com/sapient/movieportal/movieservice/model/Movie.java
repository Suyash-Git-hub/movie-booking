package com.sapient.movieportal.movieservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document("movie")
public class Movie
{
	@Id
	private String id;

	@NotEmpty(message = "Name cannot be empty")
	@NotNull(message = "Name cannot be null")
	private String name;

	private String synopsis;
	private int rating;
	private long votes;
	private String language;
	private int duration;
	private List<String> cast = new ArrayList<>();
	private List<String> theatreIds = new ArrayList<>();

	public Movie(String id, String name, String synopsis, int rating, long votes, String language, int duration,
			List<String> cast, List<String> theatreIds)
	{
		this.id = id;
		this.name = name;
		this.synopsis = synopsis;
		this.rating = rating;
		this.votes = votes;
		this.language = language;
		this.duration = duration;
		if(null != cast)
			this.cast = cast;
		if(null != theatreIds)
			this.theatreIds = theatreIds;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSynopsis()
	{
		return synopsis;
	}

	public void setSynopsis(String synopsis)
	{
		this.synopsis = synopsis;
	}

	public List<String> getCast()
	{
		return cast;
	}

	public int getRating()
	{
		return rating;
	}

	public void setRating(int rating)
	{
		this.rating = rating;
	}

	public long getVotes()
	{
		return votes;
	}

	public void setVotes(long votes)
	{
		this.votes = votes;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	public List<String> getTheatreIds()
	{
		return theatreIds;
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
		Movie other = (Movie) obj;
		return Objects.equals(id, other.id);
	}
}
