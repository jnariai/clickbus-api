package com.jnariai.clickbusplacesapi.common;

import com.github.slugify.Slugify;
import com.jnariai.clickbusplacesapi.api.PlaceRequest;
import com.jnariai.clickbusplacesapi.api.PlaceResponse;
import com.jnariai.clickbusplacesapi.domain.Place;

import java.util.List;

public class PlaceConstants {
	public static final PlaceRequest PLACE_REQUEST = new PlaceRequest("Parque teste", "Maringá", "Paraná");
	public static final PlaceResponse PLACE_RESPONSE = new PlaceResponse(1L,
			"Parque teste",
			"parque-teste",
			"Maringá",
			"Paraná");
	public static final PlaceRequest PLACE_UPDATE = new PlaceRequest("Parque do teste", "Maringá", "Paraná");
	public static final Place PLACE = new Place(1L, "Parque teste", nameToSlug("Parque teste"), "Maringá", "Paraná");
	public static final List<Place> PLACES = List.of(
			new Place(1L,
					"Parque teste",
					nameToSlug("Parque teste"),
					"Maringá",
					"Paraná"),
			new Place(2L,
					"Parque teste 2",
					nameToSlug("Parque teste 2"),
					"Maringá",
					"Paraná"),
			new Place(3L,
					"Parque teste 3",
					nameToSlug("Parque teste 3"),
					"Maringá",
					"Paraná"));

	public static final List<PlaceResponse> PLACES_RESPONSES = List.of(
			new PlaceResponse(1L,
					"Parque teste",
					nameToSlug("Parque teste"),
					"Maringá",
					"Paraná"),
			new PlaceResponse(2L,
					"Parque teste 2",
					nameToSlug("Parque teste 2"),
					"Maringá",
					"Paraná"),
			new PlaceResponse(3L,
					"Parque teste 3",
					nameToSlug("Parque teste 3"),
					"Maringá",
					"Paraná"));

	public static final List<PlaceRequest> PLACES_REQUEST = List.of(
			new PlaceRequest(
					"Parque do Ingá",
					"Maringá",
					"Paraná"),
			new PlaceRequest(
					"Parque do Japâo",
					"Maringá",
					"Paraná"),
			new PlaceRequest(
					"Parque das Grevilhas",
					"Maringá",
					"Paraná"));

	public static final PlaceResponse PLACE_TERMINAL = new PlaceResponse(1L, "Terminal", "terminal", "Maringá", "Paraná");

	public static String nameToSlug(String name) {
		return Slugify.builder().build().slugify(name);
	}
}
