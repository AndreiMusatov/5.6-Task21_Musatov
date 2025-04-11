package com.example.location.repository;

import com.example.location.models.Geodata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GeodataRepository extends CrudRepository<Geodata, Integer> {
    Optional<Geodata> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
