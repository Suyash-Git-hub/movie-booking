package com.sapient.movieportal.movieservice.searchservice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document(indexName = "city")
public class City
{
	@NotEmpty
	@NotNull(message = "Id cannot be null or empty")
	private String id;

	@NotEmpty
	@NotNull(message = "Name cannot be null or empty")
	private String name;

	private List<String> movieIds = new ArrayList<>();
	private List<String> theatreIds = new ArrayList<>();

	public City(String id, String name, List<String> movieIds, List<String> theatreIds)
	{
		this.id = id;
		this.name = name;
		if(null != movieIds)
			this.movieIds = Collections.unmodifiableList(movieIds);
		if(null != theatreIds)
			this.theatreIds = Collections.unmodifiableList(theatreIds);
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

	public List<String> getMovieIds()
	{
		return Collections.unmodifiableList(movieIds);
	}

	public void setMovieIds(List<String> movieIds)
	{
		if(null == movieIds)
			movieIds = new ArrayList<>();
		this.movieIds = Collections.unmodifiableList(movieIds);
	}

	public List<String> getTheatreIds()
	{
		return Collections.unmodifiableList(theatreIds);
	}

	public void setTheatreIds(List<String> theatreIds)
	{
		if(null == theatreIds)
			theatreIds = new ArrayList<>();
		this.theatreIds = Collections.unmodifiableList(theatreIds);
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
		City other = (City) obj;
		return Objects.equals(id, other.id);
	}
}
