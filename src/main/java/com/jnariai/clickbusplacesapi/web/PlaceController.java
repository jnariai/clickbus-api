package com.jnariai.clickbusplacesapi.web;

import com.jnariai.clickbusplacesapi.api.PlaceRequest;
import com.jnariai.clickbusplacesapi.api.PlaceResponse;
import com.jnariai.clickbusplacesapi.domain.Place;
import com.jnariai.clickbusplacesapi.domain.PlaceMapper;
import com.jnariai.clickbusplacesapi.domain.PlaceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@AllArgsConstructor
public class PlaceController {

	private PlaceService placeService;

	@PostMapping
	public ResponseEntity<PlaceResponse> createPlace(@RequestBody @Valid PlaceRequest placeRequest) {
		Place createdPlace = placeService.create(placeRequest);
		PlaceResponse placeResponse = PlaceMapper.fromPlaceToResponse(createdPlace);
		return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
	}

	@GetMapping
	public ResponseEntity<List<PlaceResponse>> getAll(@RequestParam(required = false) String name) {
		List<Place> places = this.placeService.getAll(name);
		List<PlaceResponse> placeResponses = places.stream().map(PlaceMapper::fromPlaceToResponse).toList();
		return ResponseEntity.ok().body(placeResponses);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PlaceResponse> getById(@PathVariable(required = true) Long id) {
		Place place = this.placeService.getById(id);
		PlaceResponse placeResponse = PlaceMapper.fromPlaceToResponse(place);
		return ResponseEntity.ok().body(placeResponse);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PlaceResponse> updatePlace(@PathVariable Long id,
			@RequestBody @Valid PlaceRequest placeRequest) {
		Place updatedPlace = placeService.update(id, placeRequest);
		PlaceResponse placeResponse = PlaceMapper.fromPlaceToResponse(updatedPlace);
		return ResponseEntity.ok().body(placeResponse);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
		this.placeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
