package com.dota.hero.manager.repository;

import com.dota.hero.manager.model.MatchHero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchHeroRepository extends JpaRepository<MatchHero, Long> {

    Page<MatchHero> findMatchHeroesByMatch_Id(Long matchId, Pageable pageable);

}
