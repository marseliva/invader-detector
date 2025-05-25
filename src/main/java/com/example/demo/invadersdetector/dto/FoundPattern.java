package com.example.demo.invadersdetector.dto;

import java.util.UUID;

public record FoundPattern(UUID invaderId, String name, int startRow, int startColumn) {

}
