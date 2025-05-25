package com.example.demo.invadersdetector.util;

import static java.util.function.Predicate.not;

import com.example.demo.invadersdetector.exception.ParseFileException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FileParser {

    public static List<String> parseAndValidateLines(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ParseFileException("File must not be null or empty");
        }

        try {
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.US_ASCII))) {
                lines = reader.lines()
                        .map(String::trim)
                        .filter(not(String::isEmpty))
                        .collect(Collectors.toList());
            }

            if (lines.isEmpty()) {
                throw new ParseFileException("File contains no non-empty lines");
            }
            return lines;
        } catch (IOException e) {
            throw new ParseFileException("Failed to read input file", e);
        }
    }
}
