package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DetectorFactory {

    private final Map<DetectorType, DetectorInterface> detectors;

    public DetectorFactory(List<DetectorInterface> detectors) {
        this.detectors = detectors.stream()
                .collect(
                        Collectors.toUnmodifiableMap(DetectorInterface::getType, Function.identity())
                );
    }

    public DetectorInterface getDetectorService(DetectorType type) {
        return Optional.ofNullable(detectors.get(type)).orElseThrow(
                () -> new IllegalArgumentException(String.format("DetectorService with type={%s} is not found", type)));
    }
}
