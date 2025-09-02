package com.deneme.sml.service;

import com.deneme.sml.model.City;
import com.deneme.sml.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {return cityRepository.findAll();}

    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> getCityById(Long id) {return cityRepository.findById(id);}

    public boolean deleteCityById(Long id) {
        if(cityRepository.existsById(id)){
            cityRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public City updateCityName(Long id, String newName) {
        return cityRepository.findById(id).map(city -> {
            city.setName(newName);
            return cityRepository.save(city);
        }).orElseThrow(() -> new RuntimeException("City not found with id: " + id));
    }

}
