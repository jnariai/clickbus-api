package com.jnariai.clickbusplacesapi.domain;

import com.github.slugify.Slugify;
import com.jnariai.clickbusplacesapi.api.PlaceRequest;
import com.jnariai.clickbusplacesapi.api.PlaceResponse;

import lombok.Generated;

import org.springframework.util.StringUtils;

public class PlaceMapper {

	@Generated
	private PlaceMapper() {
		throw new RuntimeException("Mapper class");
	}

	public static PlaceResponse fromPlaceToResponse(Place place) {
		return new PlaceResponse(place.getId(), place.getName(), place.getSlug(), place.getCity(), place.getState());
	}

	public static Place fromPlaceRequestToPlace(PlaceRequest placeRequest) {
		String slug = Slugify.builder().build().slugify(placeRequest.name());
		return new Place(placeRequest.name(), slug, placeRequest.city(), placeRequest.state());
	}

	public static Place updatePlacefromDto(PlaceRequest placeRequest, Place place) {
		if (StringUtils.hasText(placeRequest.name())) {
			place.setName(placeRequest.name());
		}
		if (StringUtils.hasText(placeRequest.city())) {
			place.setCity(placeRequest.city());
		}
		if (StringUtils.hasText(placeRequest.state())) {
			place.setState(placeRequest.state());
		}
		if (StringUtils.hasText(placeRequest.name())) {
			place.setSlug(Slugify.builder().build().slugify(placeRequest.name()));
		}
		return place;
	}

}
