package com.dota.hero.manager.repository;

import com.dota.hero.manager.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    Hero findByName(String name);

}
