package com.jnariai.clickbusplacesapi.domain;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.jnariai.clickbusplacesapi.common.PlaceConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
	@InjectMocks
	private PlaceService placeService;

	@Mock
	private PlaceRepository placeRepository;

	@Test
	void createPlace_WithValidData_ReturnsPlace() {
		when(placeRepository.save(any(Place.class))).thenReturn(PLACE);

		Place sut = placeService.create(PLACE_REQUEST);

		assertThat(sut).isEqualTo(PLACE);

		verify(placeRepository, times(1)).save(any(Place.class));
	}

	@Test
	void createDuplicatePlace_ThrowsEntityExistException() {
		when(placeRepository.existsByname(any())).thenReturn(true);

		assertThatThrownBy(() -> placeService.create(PLACE_REQUEST)).isInstanceOf(EntityExistsException.class);

		verify(placeRepository, times(1)).existsByname(any());
	}

	@Test
	void getPlaceById_ReturnPlace() {
		when(placeRepository.findById(PLACE.getId())).thenReturn(Optional.of(PLACE));

		Place sut = placeService.getById(PLACE.getId());

		assertThat(sut).isNotNull().isEqualTo(PLACE);

		verify(placeRepository, times(1)).findById(PLACE.getId());
	}

	@Test
	void getPlaceById_ThrowsEntityNotFound() {
		Long id = 1L;
		when(placeRepository.findById(id)).thenThrow(EntityNotFoundException.class);

		assertThatThrownBy(() -> placeService.getById(id)).isInstanceOf(EntityNotFoundException.class);

		verify(placeRepository, times(1)).findById(id);
	}

	@Test
	void listPlaces_ReturnAllPlaces() {
		when(placeRepository.findAll()).thenReturn(PLACES);

		List<Place> sut = placeService.getAll(null);

		assertThat(sut).isNotEmpty().isEqualTo(PLACES);

		verify(placeRepository, times(1)).findAll();
	}

	@Test
	void listPlaces_ReturnNoPlaces() {
		when(placeRepository.findAll()).thenReturn(Collections.emptyList());

		List<Place> sut = placeService.getAll(null);

		assertThat(sut).isEmpty();

		verify(placeRepository, times(1)).findAll();
	}

	@Test
	void listPlaces_WithName_ReturnPlacesWithName() {
		String name = "teste 2";
		when(placeRepository.findAllByNameContaining(name)).thenReturn(List.of(PLACES.get(1)));

		List<Place> sut = placeService.getAll(name);

		assertThat(sut).isNotEmpty().hasSize(1).isEqualTo(List.of(PLACES.get(1)));

		verify(placeRepository, times(1)).findAllByNameContaining(name);

	}

	@Test
	void updatePlace_WithExistingId_ReturnUpdatedPlace() {
		when(placeRepository.findById(1L)).thenReturn(Optional.of(PLACE));
		when(placeRepository.save(any(Place.class))).thenReturn(PLACE);

		Place sut = placeService.update(1L, PLACE_UPDATE);

		assertThat(sut).isNotNull().isEqualTo(PLACE);

		verify(placeRepository, times(2)).findById(1L);
		verify(placeRepository, times(1)).save(PLACE);
	}

	@Test
	void updatePlace_WithNonExistingId_ThrowException() {
		when(placeRepository.findById(99L)).thenThrow(EntityNotFoundException.class);

		assertThatThrownBy(() -> placeService.update(99L, PLACE_UPDATE)).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	void deletePlace_WithExistingId_DoesNotThrowException() {
		when(placeRepository.findById(1L)).thenReturn(Optional.of(PLACE));
		assertThatCode(() -> placeService.delete(1L)).doesNotThrowAnyException();
	}

	@Test
	void deletePlace_WithNotExistingId_ThrowsEntityNotExist() {
		lenient().doThrow(EntityNotFoundException.class).when(placeRepository).deleteById(9999L);

		assertThatThrownBy(() -> placeService.delete(9999L)).isInstanceOf(EntityNotFoundException.class);
	}

}
