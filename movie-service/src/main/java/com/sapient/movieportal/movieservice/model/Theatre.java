package com.sapient.movieportal.movieservice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document("theatre")
public class Theatre
{
	@Id
	private String id;

	@NotEmpty
	@NotNull(message = "Name cannot be null or empty")
	private String name;

	private String address;
	private String contactNo;
	private int noOfScreens;
	private List<String> showIds = new ArrayList<>();

	public Theatre(String id, String name, String address, String contactNo, int noOfScreens, List<String> showIds)
	{
		this.id = id;
		this.name = name;
		this.address = address;
		this.contactNo = contactNo;
		this.noOfScreens = noOfScreens;
		if(null != showIds)
			this.showIds = Collections.unmodifiableList(showIds);
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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

	public int getNoOfScreens()
	{
		return noOfScreens;
	}

	public void setNoOfScreens(int noOfScreens)
	{
		this.noOfScreens = noOfScreens;
	}

	public List<String> getShowIds()
	{
		return showIds;
	}

	public void setShowIds(List<String> showIds)
	{
		if(null != showIds)
			this.showIds = showIds;
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
		Theatre other = (Theatre) obj;
		return Objects.equals(id, other.id);
	}
}
