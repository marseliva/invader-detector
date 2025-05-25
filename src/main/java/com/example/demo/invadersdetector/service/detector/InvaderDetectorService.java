package com.example.demo.invadersdetector.service.detector;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.FoundPattern;
import com.example.demo.invadersdetector.dto.Invader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvaderDetectorService {

    public DetectionResult detect(final List<String> input, List<Invader> invaders, double mismatchThreshold) {
        int inputRows = input.size();
        final DetectionResult detectionResult = new DetectionResult(new ArrayList<>());
        if (inputRows == 0) {
            return detectionResult;
        }

        for (Invader invader : invaders) {
            List<String> pattern = invader.getPattern();
            if (!isInvalidPattern(input, pattern)) {
                detectionResult.foundPatterns().addAll(findInvaders(input, invader, mismatchThreshold));
            }
        }

        return detectionResult;
    }

    private List<FoundPattern> findInvaders(List<String> input, Invader invader, double mismatchThreshold) {
        List<String> pattern = invader.getPattern();
        List<FoundPattern> foundPatterns = new ArrayList<>();
        int patternRows = pattern.size();
        int patternColumns = pattern.getFirst().length();
        int inputRows = input.size();
        int inputColumns = input.getFirst().length();

        double maxErrors = patternColumns * patternRows * mismatchThreshold;
        for (int inputR = 0; inputR <= inputRows - patternRows; inputR++) {
            for (int inputC = 0; inputC <= inputColumns - patternColumns; inputC++) {
                int errors = 0;
                errors = checkPatternMatchWithNoise(input, patternRows, errors, maxErrors, patternColumns, inputR, inputC, pattern);
                if (errors <= maxErrors) {
                    foundPatterns.add(new FoundPattern(invader.getId(), invader.getName(), inputR, inputC));
                }
            }
        }
        return foundPatterns;
    }

    private int checkPatternMatchWithNoise(List<String> input, int patternRows, int errors, double maxErrors, int patternColumns, int inputR, int inputC, List<String> pattern) {
        for (int patternR = 0; patternR < patternRows; patternR++) {
            for (int patternC = 0; patternC < patternColumns; patternC++) {
                char inputValue = input.get(inputR + patternR).charAt(inputC + patternC);
                char patternValue = pattern.get(patternR).charAt(patternC);
                if (inputValue != patternValue) {
                    errors++;
                    if (errors > maxErrors) {
                        return errors;
                    }
                }
            }
        }
        return errors;
    }

    private boolean isInvalidPattern(List<String> input, List<String> invaders) {
        return invaders.size() > input.size() || invaders.getFirst().length() > input.getFirst().length();
    }
}
