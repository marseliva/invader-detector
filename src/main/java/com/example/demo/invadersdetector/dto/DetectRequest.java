package com.example.demo.invadersdetector.dto;

import com.example.demo.invadersdetector.validator.AllStringsSameLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@AllStringsSameLength
@AllArgsConstructor
public class DetectRequest {
    @NotEmpty(message = "radarData must be not empty")
    private List<@NotBlank(message = "Row in radarData must not be empty") String> radarData;
    private List<UUID> invaderIds;
}
