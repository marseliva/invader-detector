package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.dto.Invader;

import java.util.List;

public interface DetectorInterface<T> {
    DetectionResult detect(T input, List<Invader> invaders, double missmatchPercent);

    DetectorType getType();
}
