package com.example.demo.invadersdetector.service.detector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.example.demo.invadersdetector.dto.DetectionResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class JsonDetectorServiceTest {


    @InjectMocks
    private JsonDetectorService fileDetectorService;

    @Mock
    private InvaderDetectorService invaderDetectorService;

    @Test
    void whenTestDetect() {
        DetectionResult expected = new DetectionResult(Collections.emptyList());
        when(invaderDetectorService.detect(anyList(), anyList(), anyDouble())).thenReturn(expected);
        DetectionResult detectionResult = fileDetectorService.detect(Collections.emptyList(), Collections.emptyList(), 0.1);
        assertEquals(expected, detectionResult);
    }
}