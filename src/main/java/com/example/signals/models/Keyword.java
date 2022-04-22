package com.example.signals.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "keywords")
@Entity
@Getter
@Setter
public class Keyword {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}
