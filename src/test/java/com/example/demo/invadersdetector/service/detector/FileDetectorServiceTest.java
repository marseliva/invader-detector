package com.example.demo.invadersdetector.service.detector;

import static com.example.demo.invadersdetector.testUtils.MultipartFileUtils.getMultipartFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.exception.ParseFileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class FileDetectorServiceTest {

    @InjectMocks
    private FileDetectorService fileDetectorService;

    @Mock
    private InvaderDetectorService invaderDetectorService;


    @Test
    void whenFileIsEmpty() throws IOException {
        MultipartFile file = getMultipartFile("radar-data-empty.txt");
        assertThatThrownBy(() -> fileDetectorService.detect(file, Collections.emptyList(), 0.1))
                .isInstanceOf(ParseFileException.class);
    }

    @Test
    void whenFileWithEmptyLines() throws IOException {
        MultipartFile file = getMultipartFile("radar-data-empty-lines.txt");
        assertThatThrownBy(() -> fileDetectorService.detect(file, Collections.emptyList(), 0.1))
                .isInstanceOf(ParseFileException.class);
    }

    @Test
    void whenFileisValid() throws IOException {
        MultipartFile file = getMultipartFile("radar-data.txt");
        DetectionResult expected = new DetectionResult(Collections.emptyList());
        when(invaderDetectorService.detect(anyList(), anyList(), anyDouble())).thenReturn(expected);
        DetectionResult detectionResult = fileDetectorService.detect(file, Collections.emptyList(), 0.1);
        assertEquals(expected, detectionResult);

    }
}