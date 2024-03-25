package com.sapient.movieportal.movieservice.searchservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapient.movieportal.movieservice.searchservice.model.City;
import com.sapient.movieportal.movieservice.searchservice.model.Theatre;
import com.sapient.movieportal.movieservice.searchservice.repository.CityRepository;
import com.sapient.movieportal.movieservice.searchservice.repository.TheatreRepository;
import com.sapient.movieportal.movieservice.searchservice.util.TheatreNotFoundException;

public class TheatreServiceImplTest
{
	@InjectMocks
	private TheatreServiceImpl service;

	@Mock
	private TheatreRepository theatreRepo;

	@Mock
	private CityRepository cityRepo;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAllTheatres_noTheatresAvailable_shouldReturnEmptySet() throws Exception
	{
		when(theatreRepo.findAll()).thenReturn(() -> Collections.emptyIterator());

		Set<Theatre> allTheatres = service.findAllTheatres();

		assertTrue(allTheatres.isEmpty());
	}

	@Test
	public void findAllTheatres_theatresAvailable_shouldReturnCorrectSet() throws Exception
	{
		List<Theatre> theatres = new ArrayList<>();
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		theatres.add(theatre1);
		Theatre theatre2 = new Theatre("theatre-2", "PVR Saket", "Saket Mall", "+91 1234567890", 7);
		theatres.add(theatre2);
		when(theatreRepo.findAll()).thenReturn(theatres);

		Set<Theatre> allTheatres = service.findAllTheatres();

		assertEquals(2, allTheatres.size());
		assertTrue(allTheatres.contains(theatre1));
		assertTrue(allTheatres.contains(theatre2));
	}

	@Test
	public void findAllTheatresByCity_cityIdNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findAllTheatresByCity(null);
		});

		assertEquals("City Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findAllTheatresByCity_cityIdBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findAllTheatresByCity(" ");
		});

		assertEquals("City Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findAllTheatresByCity_cityNotAvailable_shouldReturnEmptySet() throws Exception
	{
		when(cityRepo.findById("city-1")).thenReturn(Optional.empty());
		
		Set<Theatre> allTheatresByCity = service.findAllTheatresByCity("city-1");
		
		assertTrue(allTheatresByCity.isEmpty());
	}

	@Test
	public void findAllTheatresByCity_noTheatresAvailableInCity_shouldReturnEmptySet() throws Exception
	{
		City ncr = new City("city-1", "Delhi NCR", null, null);
		when(cityRepo.findById("city-1")).thenReturn(Optional.of(ncr));
		
		Set<Theatre> allTheatresByCity = service.findAllTheatresByCity("city-1");
		
		assertTrue(allTheatresByCity.isEmpty());
	}

	@Test
	public void findAllTheatresByCity_theatresAvailableInCity_shouldReturnCorrectSet() throws Exception
	{
		City ncr = new City("city-1", "Delhi NCR", null, Arrays.asList("theatre-1"));
		when(cityRepo.findById("city-1")).thenReturn(Optional.of(ncr));
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		when(theatreRepo.findById("theatre-1")).thenReturn(Optional.of(theatre1));

		Set<Theatre> allTheatresByCity = service.findAllTheatresByCity("city-1");

		assertEquals(1, allTheatresByCity.size());
		assertTrue(allTheatresByCity.contains(theatre1));
	}

	@Test
	public void findTheatrebyId_theatreIdNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findTheatreById(null);
		});

		assertEquals("Theatre Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findTheatrebyId_theatreIdBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findTheatreById("");
		});

		assertEquals("Theatre Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findTheatrebyId_theatreIdNotFound_shouldThrowException() throws Exception
	{
		when(theatreRepo.findById("theatre-1")).thenReturn(Optional.empty());
		
		Exception ex = assertThrows(TheatreNotFoundException.class, () -> {
			service.findTheatreById("theatre-1");
		});

		assertEquals("No theatre found for id = theatre-1", ex.getMessage());
	}

	@Test
	public void findTheatrebyId_theatreIdAvailable_shouldReturnCorrectTheatre() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		when(theatreRepo.findById("theatre-1")).thenReturn(Optional.of(theatre1));
		
		Theatre theatre = service.findTheatreById("theatre-1");
		
		assertEquals("PVR Logix", theatre.getName());
	}

	@Test
	public void findTheatresByName_theatreNameNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findTheatresByName(null);
		});

		assertEquals("Theatre name cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findTheatresByName_theatreNameBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.findTheatresByName("");
		});

		assertEquals("Theatre name cannot be null or blank", ex.getMessage());
	}

	@Test
	public void findTheatresByName_noTheatresMatchTheSearchText_shouldReturnEmptyPage() throws Exception
	{
		when(theatreRepo.findByNameContainingIgnoreCase(eq("PVR"))).thenReturn(new ArrayList<>());
			
		Iterable<Theatre> theatresByName = service.findTheatresByName("PVR");

		assertFalse(theatresByName.iterator().hasNext());
	}

	@Test
	public void findTheatresByName_theatresMatchingTheNameAvailable_shouldReturnCorrectPage() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);
		List<Theatre> theatres = new ArrayList<>();
		theatres.add(theatre1);
		Theatre theatre2 = new Theatre("theatre-2", "PVR Saket", "Saket Mall", "+91 1234567890", 7);
		theatres.add(theatre2);
		when(theatreRepo.findByNameContainingIgnoreCase(eq("PVR"))).thenReturn(theatres);
		
		Iterable<Theatre> theatresByName = service.findTheatresByName("PVR");
		
		Iterator<Theatre> iterator = theatresByName.iterator();
		List<Theatre> theatresResult = new ArrayList<>();
		while(iterator.hasNext())
			theatresResult.add(iterator.next());
		assertEquals(2, theatresResult.size());
		assertTrue(theatresResult.contains(theatre1));
		assertTrue(theatresResult.contains(theatre2));
	}

	@Test
	public void addTheatre_theatreNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.addTheatre(null);
		});

		assertEquals("Theatre entity cannot be null", ex.getMessage());
	}

	@Test
	public void addTheatre_theatreValid_callShouldBeSentToRepo() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);

		service.addTheatre(theatre1);

		verify(theatreRepo).save(theatre1);
	}

	@Test
	public void removeTheatreById_theatreIdNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.removeTheatreById(null);
		});

		assertEquals("Theatre Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void removeTheatreById_theatreIdBlank_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.removeTheatreById("");
		});

		assertEquals("Theatre Id cannot be null or blank", ex.getMessage());
	}

	@Test
	public void removeTheatreById_idAvailable_callShouldBeSentToRepo() throws Exception
	{
		service.removeTheatreById("theatre-1");

		verify(theatreRepo).deleteById("theatre-1");
	}

	@Test
	public void updateTheatre_theatreNull_shouldThrowException() throws Exception
	{
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			service.updateTheatre(null);
		});

		assertEquals("Theatre entity cannot be null", ex.getMessage());
	}

	@Test
	public void updateTheatre_theatreValid_callShouldBeSentToRepo() throws Exception
	{
		Theatre theatre1 = new Theatre("theatre-1", "PVR Logix", "Logix City Center", "+91 9876543210", 10);

		service.updateTheatre(theatre1);

		verify(theatreRepo).save(theatre1);
	}

}
