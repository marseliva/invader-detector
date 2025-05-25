package com.example.demo.invadersdetector.controller;


import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.service.InvaderDataService;
import com.example.demo.invadersdetector.util.FileParser;
import com.example.demo.invadersdetector.validator.FileFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/invader")
@RequiredArgsConstructor
@Validated
public class InvaderController {

    private final InvaderDataService invaderDataService;

    @PostMapping
    public ResponseEntity<Invader> createInvader(@Valid @RequestBody Invader request) {
        Invader invader = invaderDataService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(invader);
    }

    @PostMapping(
            path = "/{invaderName}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Invader> createInvader(@RequestPart("file")
                                                 @NotNull(message = "File must be provided")
                                                 @FileFormat(allowed = {"txt"})
                                                 @Valid
                                                 MultipartFile multipartFile,
                                                 @PathVariable("invaderName")
                                                 String invaderName) {
        Invader newInvader = Invader.builder().name(invaderName).pattern(FileParser.parseAndValidateLines(multipartFile)).build();
        newInvader = invaderDataService.save(newInvader);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvader);
    }

    @GetMapping
    public ResponseEntity<List<Invader>> getAllInvaders() {
        return ResponseEntity.ok(invaderDataService.getAllInvaders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invader> getInvaderById(@PathVariable UUID id) {
        return ResponseEntity.ok(invaderDataService.getInvaderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvader(@PathVariable UUID id) {
        invaderDataService.deleteInvader(id);
        return ResponseEntity.noContent().build();
    }
}
