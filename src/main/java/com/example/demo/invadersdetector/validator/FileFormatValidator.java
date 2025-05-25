package com.example.demo.invadersdetector.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFormatValidator implements ConstraintValidator<FileFormat, MultipartFile> {

    private List<String> allowedExtensions;

    @Override
    public void initialize(FileFormat annotation) {
        allowedExtensions = Arrays.stream(annotation.allowed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return false;
        }

        String filename = multipartFile.getOriginalFilename();
        if (!StringUtils.hasText(filename) || !filename.contains(".")) {
            return false;
        }

        String ext = filename.substring(filename.lastIndexOf('.') + 1)
                .toLowerCase();
        return allowedExtensions.contains(ext);
    }
}
