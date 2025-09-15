package com.dota.hero.manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "bonus_strength", nullable = false)
    private Integer bonusStrength;

    @Column(name = "bonus_agility", nullable = false)
    private Integer bonusAgility;

    @Column(name = "bonus_intelligence", nullable = false)
    private Integer bonusIntelligence;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "items")
    private List<MatchHero> matchHeroes;

}
