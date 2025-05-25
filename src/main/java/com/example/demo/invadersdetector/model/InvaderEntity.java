package com.example.demo.invadersdetector.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invader")
@NoArgsConstructor
@Getter
@Setter
public class InvaderEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ElementCollection
    private List<String> pattern;

    public InvaderEntity(String name, List<String> pattern) {
        this.name = name;
        this.pattern = pattern;
    }
}
