package com.example.demo.invadersdetector.repository;

import com.example.demo.invadersdetector.model.InvaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvaderRepository extends JpaRepository<InvaderEntity, UUID> {
}
