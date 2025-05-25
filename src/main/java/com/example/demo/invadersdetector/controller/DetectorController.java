package com.example.demo.invadersdetector.controller;

import com.example.demo.invadersdetector.dto.DetectRequest;
import com.example.demo.invadersdetector.dto.DetectionResult;
import com.example.demo.invadersdetector.dto.DetectorType;
import com.example.demo.invadersdetector.service.detector.DetectorService;
import com.example.demo.invadersdetector.validator.FileFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/detect")
@Validated
@RequiredArgsConstructor
public class DetectorController {

    private final DetectorService detectorService;

    @PostMapping(
            path = "/{mismatchThreshold}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetectionResult> detectText(@Valid @RequestBody
                                                      DetectRequest request,
                                                      @PathVariable(value = "mismatchThreshold")
                                                      double mismatchThreshold) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(detectorService.getDetectionResult(request.getRadarData(),
                        DetectorType.JSON,
                        request.getInvaderIds(),
                        mismatchThreshold));
    }


    @PostMapping(
            path = "/{invaderIds:.+}/{mismatchThreshold}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DetectionResult> detectText(@RequestPart("file")
                                                      @NotNull(message = "File must be provided")
                                                      @FileFormat(allowed = {"txt"})
                                                      @Valid
                                                      MultipartFile multipartFile,
                                                      @PathVariable("invaderIds")
                                                      List<UUID> invaderIds,
                                                      @PathVariable(value = "mismatchThreshold")
                                                      double mismatchThreshold) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(detectorService.getDetectionResult(multipartFile, DetectorType.FILE, invaderIds, mismatchThreshold));
    }
}

