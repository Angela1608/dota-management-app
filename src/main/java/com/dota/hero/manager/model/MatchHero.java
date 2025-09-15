package com.dota.hero.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "match_heroes")
public class MatchHero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id", nullable = false)
    private Hero hero;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "match_hero_items",
            joinColumns = @JoinColumn(name = "match_hero_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    @JsonIgnore
    public int getTotalHealth() {
        int base = hero.getBaseHealth();
        int bonus = items == null ? 0 : items.stream()
                .mapToInt(Item::getBonusStrength)
                .sum();
        return base + bonus;
    }

    @JsonIgnore
    public int getTotalMana() {
        int base = hero.getBaseMana();
        int bonus = items == null ? 0 : items.stream()
                .mapToInt(Item::getBonusIntelligence)
                .sum();
        return base + bonus;
    }

    @JsonIgnore
    public int getTotalStrength() {
        return items == null ? 0 : items.stream()
                .mapToInt(Item::getBonusStrength)
                .sum();
    }

    @JsonIgnore
    public int getTotalAgility() {
        return items == null ? 0 : items.stream()
                .mapToInt(Item::getBonusAgility)
                .sum();
    }

    @JsonIgnore
    public int getTotalIntelligence() {
        return items == null ? 0 : items.stream()
                .mapToInt(Item::getBonusIntelligence)
                .sum();
    }

}
