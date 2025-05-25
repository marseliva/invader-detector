package com.example.demo.invadersdetector.dto;

import java.util.List;


public record DetectionResult(List<FoundPattern> foundPatterns) {
}
