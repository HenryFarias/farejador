package com.example.farejador;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> T map(S source, Class<T> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> clazz) {
        return source.stream()
                    .map(element -> modelMapper.map(element, clazz))
                    .collect(Collectors.toList());
    }

}
