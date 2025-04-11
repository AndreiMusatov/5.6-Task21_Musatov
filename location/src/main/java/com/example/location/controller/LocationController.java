package com.example.location.controller;

import com.example.location.models.Geodata;
import com.example.location.models.Weather;
import com.example.location.repository.GeodataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private GeodataRepository repository;
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${appid}")
    private String appId;
//    @Value("${weather.url}")
//    private String weatherUrl;



    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Geodata geodata = repository.findByName(name).get();
        String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s&units=metric&appid=%s", geodata.getLat(), geodata.getLon(), appId);
        return restTemplate.getForObject(url, Weather.class);
    }


    @GetMapping
    public Optional<Geodata> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Geodata save(@RequestBody Geodata geodata) {
        return repository.save(geodata);
    }

    @GetMapping("/all")
    public Iterable<Geodata> findAll() {
        return repository.findAll();
    }

    @PutMapping
    public ResponseEntity<Geodata> updateLocation(@RequestParam String name, @RequestBody Geodata geodata) {
        HttpStatus status = repository.existsByName(name) ? HttpStatus.OK : HttpStatus.CREATED;
        geodata.setName(name);
        return new ResponseEntity(repository.save(geodata), status);
    }


    @DeleteMapping
    public void deleteLocation(@RequestParam String name) {
        repository.deleteByName(name);
    }
}
