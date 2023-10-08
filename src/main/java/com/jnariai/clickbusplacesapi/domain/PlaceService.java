package com.jnariai.clickbusplacesapi.domain;

import com.jnariai.clickbusplacesapi.api.PlaceRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {
	private PlaceRepository placeRepository;

	public List<Place> getAll(String name) {
		if (name != null) {
			return this.placeRepository.findAllByNameContaining(name);
		}
		return this.placeRepository.findAll();
	}

	public Place getById(Long id) {
		return this.placeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Place with id " + id + " not found"));
	}

	public Place create(PlaceRequest placeRequest) {
		if (this.placeRepository.existsByname(placeRequest.name())) {
			throw new EntityExistsException("Place with name " + (placeRequest.name()) + " already exists");
		}
		Place place = PlaceMapper.fromPlaceRequestToPlace(placeRequest);
		return this.placeRepository.save(place);
	}

	public Place update(Long id, PlaceRequest placeRequest) {
		Place place = this.getById(id);
		Place updatedPlace = PlaceMapper.updatePlacefromDto(placeRequest, place);
		this.placeRepository.save(updatedPlace);
		return this.getById(id);
	}

	public void delete(Long id) {
		this.getById(id);
		this.placeRepository.deleteById(id);
	}
}
