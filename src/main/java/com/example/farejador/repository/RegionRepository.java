package com.example.farejador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.farejador.enums.RegionTypeEnum;
import com.example.farejador.models.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findByType(RegionTypeEnum regionType);
}
