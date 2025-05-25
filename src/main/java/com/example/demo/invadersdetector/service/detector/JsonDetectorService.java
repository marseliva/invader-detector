package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.dto.Invader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonDetectorService implements DetectorInterface<List<String>> {

    private final InvaderDetectorService invaderDetectorService;

    @Override
    public DetectionResult detect(List<String> input, List<Invader> invaders, double missmatchPercent) {
        return invaderDetectorService.detect(input, invaders, missmatchPercent);
    }

    @Override
    public DetectorType getType() {
        return DetectorType.JSON;
    }
}
