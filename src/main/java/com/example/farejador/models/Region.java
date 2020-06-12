package com.example.farejador.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.farejador.enums.IntentionEnum;
import com.example.farejador.enums.RegionTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Region {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String xpath;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private RegionTypeEnum type;

    @OneToMany(mappedBy="region", fetch = FetchType.EAGER)
    private List<Neighborhood> neighborhoods;
}
