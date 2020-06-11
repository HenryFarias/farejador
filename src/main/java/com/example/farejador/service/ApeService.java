package com.example.farejador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.farejador.RegionTypeEnum;
import com.example.farejador.models.Ape;
import com.example.farejador.repository.ApeRepository;

@Service
public class ApeService {

    private final ApeRepository apeRepository;

    @Autowired
    public ApeService(ApeRepository apeRepository) {
        this.apeRepository = apeRepository;
    }

    public List<Ape> search(boolean visualized, RegionTypeEnum regionType) {
        List<Ape> apes = findAllByVisualizedAndFavoriteSystemAndRegion_Type(visualized, true, regionType);

        apes.forEach(ape -> {
            ape.setVisualized(true);
            apeRepository.save(ape);
        });

        return apes;
    }

    List<Ape> findAllByVisualizedAndFavoriteSystemAndRegion_Type(boolean visualized, boolean favoriteSystem, RegionTypeEnum regionType) {
        return apeRepository.findAllByVisualizedAndFavoriteSystemAndRegion_Type(visualized, true, regionType);
    }

    public List<Ape> findAllByVisualizedAndFavoriteSystem(boolean visualized, boolean favoriteSystem) {
        return apeRepository.findAllByVisualizedAndFavoriteSystem(visualized, favoriteSystem);
    }

    public List<Ape> findAllFavorites() {
        return apeRepository.findAllByFavorite(true);
    }
}
