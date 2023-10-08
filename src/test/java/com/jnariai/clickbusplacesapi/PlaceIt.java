package com.jnariai.clickbusplacesapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jnariai.clickbusplacesapi.api.PlaceResponse;

import static com.jnariai.clickbusplacesapi.common.PlaceConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/remove-places.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = { "/insert-places.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PlaceIt {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void createPlace_ReturnsCreated() {
    PlaceResponse sut = webTestClient.post()
        .uri("/places")
        .bodyValue(PLACE_REQUEST)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(PlaceResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(sut.id()).isNotNull();
    assertThat(sut.name()).isEqualTo(PLACE_REQUEST.name());
    assertThat(sut.city()).isEqualTo(PLACE_REQUEST.city());
    assertThat(sut.state()).isEqualTo(PLACE_REQUEST.state());
    assertThat(sut.slug()).isEqualTo(nameToSlug(PLACE_REQUEST.name()));
  }

  @Test
  public void listPlaces_ReturnsAllPlaces() {
    List<PlaceResponse> sut = webTestClient.get()
        .uri("/places")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(PlaceResponse.class)
        .returnResult().getResponseBody();

    assertThat(sut).hasSize(2);
  }

  @Test
  public void listPlacesByName_ReturnsPlacesByName() {
    List<PlaceResponse> sut = webTestClient.get()
        .uri("/places?name=Terminal")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(PlaceResponse.class)
        .returnResult().getResponseBody();

    assertThat(sut).hasSize(1);
    assertThat(sut.get(0).name()).contains("Terminal");

  }

  @Test
  public void getPlace_ReturnsPlace() {

    PlaceResponse sut = webTestClient.get()
        .uri("places/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody(PlaceResponse.class)
        .returnResult().getResponseBody();

    assertThat(sut).isEqualTo(PLACE_TERMINAL);
  }

  @Test
  public void updatePlace_ReturnsPlaceModified() {

    PlaceResponse sut = webTestClient.put()
        .uri("/places/1")
        .bodyValue(PLACE_REQUEST)
        .exchange()
        .expectStatus().isOk()
        .expectBody(PlaceResponse.class)
        .returnResult().getResponseBody();

    assertThat(sut.id()).isEqualTo(1);
    assertThat(sut.name()).isEqualTo(PLACE_REQUEST.name());
    assertThat(sut.city()).isEqualTo(PLACE_REQUEST.city());
    assertThat(sut.state()).isEqualTo(PLACE_REQUEST.state());
  }

  @Test
  public void deletePlace_ReturnsNoContent() {

    webTestClient.delete().uri("places/1")
        .exchange()
        .expectStatus().isNoContent();

    webTestClient.get().uri("/places/1")
        .exchange()
        .expectStatus().isNotFound();
  }

}
