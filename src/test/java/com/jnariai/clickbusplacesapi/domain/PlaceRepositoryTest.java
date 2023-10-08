package com.jnariai.clickbusplacesapi.domain;

import static com.jnariai.clickbusplacesapi.common.PlaceConstants.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PlaceRepositoryTest {

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@AfterEach
	public void afterEach() {
		PLACE.setId(null);
	}

	@Test
	void createPlace_WithValidData_ReturnsPlace() {
		Place place = placeRepository.save(PLACE);

		Place sut = testEntityManager.find(Place.class, place.getId());

		assertThat(sut).isNotNull();
		assertThat(sut.getName()).isEqualTo(PLACE.getName());
		assertThat(sut.getSlug()).isEqualTo(PLACE.getSlug());
		assertThat(sut.getCity()).isEqualTo(PLACE.getCity());
		assertThat(sut.getState()).isEqualTo(PLACE.getState());
	}

	@Test
	void createPlace_WithInvalidData_ThrowsException() {
		Place emptyPlace = new Place();
		Place invalidPlace = new Place("", "", "", "");
		assertThatThrownBy(() -> placeRepository.save(emptyPlace)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> placeRepository.save(invalidPlace)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void createDuplicatePlace_ThrowsException() {
		Place place = testEntityManager.persistFlushFind(PLACE);

		testEntityManager.detach(place);
		place.setId(null);

		assertThatThrownBy(() -> placeRepository.save(place)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void getPlace_WithExistingId_ReturnsPlace() {

		Place place = testEntityManager.persistFlushFind(PLACE);

		Optional<Place> sutOptional = placeRepository.findById(place.getId());

		assertThat(sutOptional).isNotEmpty();
		Place sut = sutOptional.get();

		assertThat(sut).isNotNull();
		assertThat(sut).isEqualTo(place);
	}

	@Test
	void getPlace_WithNonExistingId_ReturnsEmpty() {
		Optional<Place> sut = placeRepository.findById(1L);

		assertThat(sut).isEmpty();
	}

	@Test
	void listPlaces_ReturnsPlaces() {
		PLACES.forEach(testEntityManager::merge);

		List<Place> sut = placeRepository.findAll();
		List<Place> sut2 = placeRepository.findAllByNameContaining("teste 2");

		assertThat(sut).isNotEmpty();
		assertThat(sut).hasSize(3);

		assertThat(sut2).isNotEmpty();
		assertThat(sut2).hasSize(1);
	}

	@Test
	void listPlaces_ReturnsNoPlaces() {
		List<Place> sut = placeRepository.findAll();
		List<Place> sut2 = placeRepository.findAllByNameContaining(anyString());

		assertThat(sut).isEmpty();
		assertThat(sut2).isEmpty();
	}

	@Test
	void deletePlace_WithExistingId_ReturnsNoContent(){
		Place place = testEntityManager.persistFlushFind(PLACE);

		placeRepository.deleteById(PLACE.getId());

		Place sut = testEntityManager.find(Place.class, place.getId());

		assertThat(sut).isNull();
	}

}
