package com.example.farejador.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ape {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String link;

    @Column
    private String description;

    @Column
    private boolean visualized;

    @Column
    private boolean favoriteHenry;

    @Column
    private boolean favoriteSimone;

    @Column
    private boolean favorite;

    @Column
    private boolean favoriteSystem;

}
