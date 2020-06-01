package com.example.farejador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.farejador.models.Ape;
import com.example.farejador.repository.ApeRepository;

@Service
public class ApeService {

    private final ApeRepository apeRepository;

    @Autowired
    public ApeService(ApeRepository apeRepository) {
        this.apeRepository = apeRepository;
    }

    public List<Ape> findAllByVisualized(boolean visualized) {
        List<Ape> apes = findAllByVisualizedAndFavoriteSystem(visualized, true);

        apes.forEach(ape -> {
            ape.setVisualized(true);
            apeRepository.save(ape);
        });

        return apes;
    }

    public List<Ape> findAllByVisualizedAndFavoriteSystem(boolean visualized, boolean favoriteSystem) {
        return apeRepository.findAllByVisualizedAndFavoriteSystem(visualized, favoriteSystem);
    }

    public List<Ape> findAllFavorites() {
        return apeRepository.findAllByFavorite(true);
    }
}
