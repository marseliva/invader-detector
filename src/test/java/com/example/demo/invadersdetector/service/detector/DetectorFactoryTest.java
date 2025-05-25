package com.example.demo.invadersdetector.service.detector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.dto.Invader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DetectorFactoryTest.TestConfig.class, DetectorFactory.class})
class DetectorFactoryTest {

    @Autowired
    private DetectorFactory detectorFactory;

    @Configuration
    static class TestConfig {
        @Bean
        public DetectorInterface<MultipartFile> fileDetector() {
            return new DetectorInterface<>() {
                @Override
                public DetectionResult detect(MultipartFile input, List<Invader> invaders, double missmatchPercent) {
                    return new DetectionResult(Collections.emptyList());
                }

                @Override
                public DetectorType getType() {
                    return DetectorType.FILE;
                }
            };
        }

        @Bean
        public DetectorInterface<List<String>> integerDetector() {
            return new DetectorInterface<>() {
                @Override
                public DetectionResult detect(List<String> input, List<Invader> invaders, double missmatchPercent) {
                    return new DetectionResult(Collections.emptyList());
                }

                @Override
                public DetectorType getType() {
                    return DetectorType.JSON;
                }
            };
        }
    }

    @Test
    void whenValidType_thenReturnCorrectDetector() {
        DetectorInterface<MultipartFile> fileService = detectorFactory.getDetectorService(DetectorType.FILE);
        assertThat(fileService).isNotNull();

        DetectorInterface<List<String>> jsonService = detectorFactory.getDetectorService(DetectorType.JSON);
        assertThat(jsonService).isNotNull();
    }

    @Test
    void whenInvalidType_thenThrowException() {
        assertThatThrownBy(() -> detectorFactory.getDetectorService(DetectorType.IMAGE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
