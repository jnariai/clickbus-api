package com.jnariai.clickbusplacesapi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnariai.clickbusplacesapi.api.PlaceRequest;
import com.jnariai.clickbusplacesapi.domain.PlaceService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.jnariai.clickbusplacesapi.common.PlaceConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlaceService placeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void createPlace_WithValidData_ReturnsCreated() throws Exception {
		when(placeService.create(PLACE_REQUEST)).thenReturn(PLACE);

		mockMvc.perform(post("/places").content(objectMapper.writeValueAsString(PLACE_REQUEST))
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.id").value(PLACE_RESPONSE.id()))
						.andExpect(jsonPath("$.name").value(PLACE_RESPONSE.name()))
						.andExpect(jsonPath("$.slug").value(PLACE_RESPONSE.slug()))
						.andExpect(jsonPath("$.city").value(PLACE_RESPONSE.city()))
						.andExpect(jsonPath("$.state").value(PLACE_RESPONSE.state()));

	}

	@Test
	void createPlace_WithInvalidData_ReturnsBadRequest() throws Exception {
		PlaceRequest invalidPlace = new PlaceRequest(null, null, null);

		mockMvc.perform(
				post("/places").content(objectMapper.writeValueAsString(invalidPlace)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	void createPlace_WithExistingName_ReturnsConflict() throws Exception{
		when(placeService.create(any(PlaceRequest.class))).thenThrow(EntityExistsException.class);

		mockMvc.perform(
		post("/places").content(objectMapper.writeValueAsString(PLACE_REQUEST)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());
	}

	@Test
	void getPlace_WithExistingId_ReturnsPlace() throws Exception {
		when(placeService.getById(1L)).thenReturn(PLACE);

		mockMvc.perform(get("/places/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(PLACE.getId()))
				.andExpect(jsonPath("$.name").value(PLACE.getName()))
				.andExpect(jsonPath("$.slug").value(PLACE.getSlug()))
				.andExpect(jsonPath("$.city").value(PLACE.getCity()))
				.andExpect(jsonPath("$.state").value(PLACE.getState()));
	}

	@Test
	void getPlace_WithNonExistingId_ThrowsEntityNotFound() throws Exception {
		when(placeService.getById(any(Long.class))).thenThrow(EntityNotFoundException.class);

		mockMvc.perform(get("/places/1")).andExpect(status().isNotFound());
	}

	@Test
	void listPlaces_ReturnsPlaces() throws Exception{
		when(placeService.getAll(null)).thenReturn(PLACES);

		when(placeService.getAll(PLACES.get(1).getName())).thenReturn(List.of(PLACES.get(1)));

		mockMvc.perform(get("/places"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$", hasSize(3)));

		mockMvc.perform(get("/places?" + String.format("name=%s", PLACES.get(1).getName())))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void listPlaces_ReturnsNoPlaces() throws Exception {
		mockMvc.perform(get("/places"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void updatePlace_WithExistingId_ReturnsPlace() throws JsonProcessingException, Exception {
		when(placeService.update(anyLong(),any(PlaceRequest.class))).thenReturn(PLACE);

		mockMvc.perform(put("/places/1")
				.content(objectMapper.writeValueAsString(PLACE_REQUEST))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(PLACE_RESPONSE.id()))
				.andExpect(jsonPath("$.name").value(PLACE_RESPONSE.name()))
				.andExpect(jsonPath("$.slug").value(PLACE_RESPONSE.slug()))
				.andExpect(jsonPath("$.city").value(PLACE_RESPONSE.city()))
				.andExpect(jsonPath("$.state").value(PLACE_RESPONSE.state()));

	}

	@Test
	void deletePlace_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/places/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	void deletePlace_WithNonExistingId_ThrowsEntityNotFound() throws Exception {
		doThrow(EntityNotFoundException.class).when(placeService).delete(1L);

		mockMvc.perform(delete("/places/1"))
				.andExpect(status().isNotFound());
	}

}
