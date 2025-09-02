package com.deneme.sml.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deneme.sml.model.City;
import com.deneme.sml.service.CityService;

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
        try {
            City savedCity = cityService.saveCity(city);
            return ResponseEntity.ok(savedCity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id)          // Optional<City>
                .map(ResponseEntity::ok)            // varsa 200 + body
                .orElseGet(() -> ResponseEntity.notFound().build()); // yoksa 404
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCityById(@PathVariable Long id){
        boolean deleted = cityService.deleteCityById(id);
        if(deleted){
            return ResponseEntity.ok("✅ City deleted successfully (ID: " + id + ")");
        }
        else return ResponseEntity.status(404).body("❌ City not found (ID: " + id + ")");

    }
    @PutMapping("/updateName/{id}")
    public ResponseEntity<City> updateCityName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String newName = body.get("name");
            City updatedCity = cityService.updateCityName(id, newName);
            return ResponseEntity.ok(updatedCity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
