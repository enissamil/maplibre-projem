package com.deneme.sml.controller;

import com.deneme.sml.model.City;
import com.deneme.sml.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Yeni şehir kaydet
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @GetMapping("/allCities")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }


    // İsme göre şehir getir
    @GetMapping("/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable String name) {
        Optional<City> city = cityService.getCityByName(name);
        return city.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id)          // Optional<City>
                .map(ResponseEntity::ok)            // varsa 200 + body
                .orElseGet(() -> ResponseEntity.notFound().build()); // yoksa 404
    }

}
