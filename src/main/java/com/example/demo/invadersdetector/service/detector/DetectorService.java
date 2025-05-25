package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.service.InvaderDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetectorService {

    private final DetectorFactory detectorFactory;
    private final InvaderDataService invaderDataService;

    public <T> DetectionResult getDetectionResult(T inputData,
                                                  DetectorType detectorType,
                                                  List<UUID> invadersIds,
                                                  double mismatchThreshold) {
        List<Invader> invaders = new ArrayList<>();
        if (CollectionUtils.isEmpty(invadersIds)) {
            invaders.addAll(invaderDataService.getAllInvaders());
        } else {
            invaders.addAll(invaderDataService.getInvadersById(invadersIds));
        }
        if (CollectionUtils.isEmpty(invaders)) {
            return new DetectionResult(Collections.emptyList());
        }
        return detectorFactory.getDetectorService(detectorType).detect(inputData, invaders, mismatchThreshold);
    }
}
