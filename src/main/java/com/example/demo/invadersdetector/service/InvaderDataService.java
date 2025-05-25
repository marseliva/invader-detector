package com.example.demo.invadersdetector.service;


import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.mapper.InvaderMapper;
import com.example.demo.invadersdetector.model.InvaderEntity;
import com.example.demo.invadersdetector.repository.InvaderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvaderDataService {
    private final InvaderRepository invaderRepository;

    @Transactional
    public Invader save(Invader request) {
        InvaderEntity entity = InvaderMapper.toEntity(request);
        entity = invaderRepository.save(entity);
        return InvaderMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<Invader> getAllInvaders() {
        return invaderRepository.findAll().stream()
                .map(InvaderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Invader> getInvadersById(List<UUID> ids) {
        return invaderRepository.findAllById(ids).stream().map(InvaderMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Invader getInvaderById(UUID id) {
        return invaderRepository.findById(id)
                .map(InvaderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Invader with id={%s} not found", id)));
    }

    @Transactional
    public void deleteInvader(UUID id) {
        invaderRepository.deleteById(id);
    }

}
