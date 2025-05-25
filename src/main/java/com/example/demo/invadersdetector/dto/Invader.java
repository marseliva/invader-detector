package com.example.demo.invadersdetector.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Invader {
    private UUID id;
    @NotBlank(message = "Invader name must not be empty")
    private String name;
    @NotEmpty(message = "Invader pattern must be not empty")
    private List<@NotBlank(message = "Row in invader pattern must not be empty") String> pattern;
}
