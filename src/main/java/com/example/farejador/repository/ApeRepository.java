package com.example.farejador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.farejador.RegionTypeEnum;
import com.example.farejador.models.Ape;

@Repository
public interface ApeRepository extends JpaRepository<Ape, Long> {

    List<Ape> findAllByVisualizedAndFavoriteSystem(boolean visualized, boolean favoriteSystem);
    List<Ape> findAllByVisualizedAndFavoriteSystemAndRegion_Type(boolean visualized, boolean favoriteSystem, RegionTypeEnum regionType);
    List<Ape> findAllByFavorite(boolean favorite);
    boolean existsByLink(String link);

}
