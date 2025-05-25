package com.example.demo.invadersdetector.testUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileUtils {

    public static MockMultipartFile getMultipartFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        try (InputStream is = resource.getInputStream()) {
            return new MockMultipartFile(
                    "file",
                    resource.getFilename(),
                    "text/plain",
                    is.readAllBytes()
            );
        }
    }
}
