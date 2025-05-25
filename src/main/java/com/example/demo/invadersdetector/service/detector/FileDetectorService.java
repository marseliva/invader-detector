package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.util.FileParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileDetectorService implements DetectorInterface<MultipartFile> {

    private final InvaderDetectorService invaderDetectorService;

    @Override
    public DetectionResult detect(MultipartFile input, List<Invader> invaders, double mismatchThreshold) {
        List<String> inputData = FileParser.parseAndValidateLines(input);
        return invaderDetectorService.detect(inputData, invaders, mismatchThreshold);
    }

    @Override
    public DetectorType getType() {
        return DetectorType.FILE;
    }
}
