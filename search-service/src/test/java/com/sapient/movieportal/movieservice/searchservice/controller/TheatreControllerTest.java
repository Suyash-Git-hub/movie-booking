package com.sapient.movieportal.movieservice.searchservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sapient.movieportal.movieservice.searchservice.model.Theatre;
import com.sapient.movieportal.movieservice.searchservice.service.TheatreService;
import com.sapient.movieportal.movieservice.searchservice.util.TheatreNotFoundException;

public class TheatreControllerTest
{
	@InjectMocks
	private TheatreController controller;

	@Mock
	private TheatreService service;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllTheatres_noTheatresExist_shouldReturnEmptySet() throws Exception
	{
		when(service.findAllTheatres()).thenReturn(new HashSet<>());
		
		Set<Theatre> allTheatres = controller.getAllTheatres();
		
		assertTrue(allTheatres.isEmpty());
	}

	@Test
	public void getAllTheatres_theatresAvailable_shouldReturnCorrectSet() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		Theatre theatre2 = new Theatre("theatre-2", "PVR Saket", "Saket Mall", "+91 1234567890", 7);
		when(service.findAllTheatres()).thenReturn(new HashSet<>(Arrays.asList(theatre1, theatre2)));
		
		Set<Theatre> allTheatres = controller.getAllTheatres();
		
		assertEquals(2, allTheatres.size());
	}

	@Test
	public void getAllTheatresByCity_noTheatresAvailableForCity_shouldReturnEmptySet() throws Exception
	{
		when(service.findAllTheatresByCity("city-1")).thenReturn(new HashSet<>());
		
		Set<Theatre> allTheatresByCity = controller.getAllTheatresByCity("city-1");
		
		assertTrue(allTheatresByCity.isEmpty());
	}

	@Test
	public void getAllTheatresByCity_theatresAvailableForCity_shouldReturnCorrectSet() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		Theatre theatre2 = new Theatre("theatre-2", "PVR Saket", "Saket Mall", "+91 1234567890", 7);
		when(service.findAllTheatresByCity("city-1")).thenReturn(new HashSet<>(Arrays.asList(theatre1, theatre2)));
		
		Set<Theatre> allTheatresByCity = controller.getAllTheatresByCity("city-1");
		
		assertEquals(2, allTheatresByCity.size());
		assertTrue(allTheatresByCity.contains(theatre1));
		assertTrue(allTheatresByCity.contains(theatre2));
	}

	@Test
	public void getTheatreById_noTheatreAvailable_shouldThrowException() throws Exception
	{
		when(service.findTheatreById("theatre-1")).thenThrow(new TheatreNotFoundException("No theatre found for id = theatre-1"));
		
		Exception ex = assertThrows(TheatreNotFoundException.class, () -> {
			controller.getTheatreById("theatre-1");
		});

		assertEquals("No theatre found for id = theatre-1", ex.getMessage());
	}

	@Test
	public void getTheatreById_theatreAvailable_shouldReturnEntity() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		when(service.findTheatreById("theatre-1")).thenReturn(theatre1);
		
		Theatre theatreById = controller.getTheatreById("theatre-1");
		
		assertEquals(theatreById, theatre1);
	}

	@Test
	public void searchTheatresByName_noTheatreFound_shouldReturnEmptyPage() throws Exception
	{
		when(service.findTheatresByName("PVR")).thenReturn(Page.empty());
		
		Iterable<Theatre> theatresByName = controller.searchTheatresByName("PVR");
		
		assertFalse(theatresByName.iterator().hasNext());
	}

	@Test
	public void searchTheatresByName_multipleTheatresFound_shouldReturnCorrectPage() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		Theatre theatre2 = new Theatre("theatre-2", "PVR Saket", "Saket Mall", "+91 1234567890", 7);
		Page<Theatre> theatrePages = new PageImpl<Theatre>(Arrays.asList(theatre1, theatre2));
		when(service.findTheatresByName("PVR")).thenReturn(theatrePages);
		
		Iterable<Theatre> theatresByName = controller.searchTheatresByName("PVR");
		
		Iterator<Theatre> iterator = theatresByName.iterator();
		List<Theatre> theatres = new ArrayList<>();
		while(iterator.hasNext())
			theatres.add(iterator.next());
		assertEquals(2, theatres.size());
		assertTrue(theatres.contains(theatre1));
		assertTrue(theatres.contains(theatre2));
	}

	@Test
	public void addTheatre_theatreValid_callShouldBeSentToTheService() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);

		controller.addTheatre(theatre1);

		verify(service).addTheatre(theatre1);
	}

	@Test
	public void deleteTheatreById_theatreIdValid_callShouldBeSentToTheService() throws Exception
	{
		controller.deleteTheatreById("theatre-1");

		verify(service).removeTheatreById("theatre-1");
	}

	@Test
	public void updateTheatre_theatreValid_callShouldBeSentToTheService() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);

		controller.updateTheatre(theatre1);

		verify(service).updateTheatre(theatre1);
	}
}
