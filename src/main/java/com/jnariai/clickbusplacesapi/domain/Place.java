package com.jnariai.clickbusplacesapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotEmpty
	@Column(nullable = false, unique = true)
	String name;

	@NotEmpty
	@Column(nullable = false)
	String slug;

	@NotEmpty
	@Column(nullable = false)
	String city;

	@NotEmpty
	@Column(nullable = false)
	String state;

	@CreatedDate
	LocalDateTime createdAt;

	@LastModifiedDate
	LocalDateTime updatedAt;

	public Place(String name, String slug, String city, String state) {
		this.name = name;
		this.slug = slug;
		this.city = city;
		this.state = state;
	}

	public Place(Long id, String name, String slug, String city, String state) {
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.city = city;
		this.state = state;
	}

}
