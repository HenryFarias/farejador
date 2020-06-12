package com.example.farejador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.farejador.enums.RegionTypeEnum;
import com.example.farejador.models.Region;
import com.example.farejador.repository.RegionRepository;

@Service
public class RegionService {

    private final RegionRepository repository;

    @Autowired
    public RegionService(RegionRepository repository) {
        this.repository = repository;
    }

    public Region findByType(RegionTypeEnum regionType) {
        return repository.findByType(regionType);
    }
}
