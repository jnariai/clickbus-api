package com.jnariai.clickbusplacesapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
	List<Place> findAllByNameContaining(String name);

	boolean existsByname(String name);
}
