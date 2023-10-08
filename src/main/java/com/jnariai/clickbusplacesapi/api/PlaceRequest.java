package com.jnariai.clickbusplacesapi.api;

import jakarta.validation.constraints.NotBlank;

public record PlaceRequest(@NotBlank(message = "Name is mandatory") String name,
    @NotBlank(message = "City is mandatory") String city,
    @NotBlank(message = "State is mandatory") String state) {}
