package com.example.demo.invadersdetector.mapper;

import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.model.InvaderEntity;
import lombok.experimental.UtilityClass;


@UtilityClass
public class InvaderMapper {

    public Invader toDto(InvaderEntity entity) {
        return Invader.builder().id(entity.getId()).name(entity.getName()).pattern(entity.getPattern()).build();
    }

    public static InvaderEntity toEntity(Invader invader) {
        return new InvaderEntity(invader.getName(), invader.getPattern());
    }
}
