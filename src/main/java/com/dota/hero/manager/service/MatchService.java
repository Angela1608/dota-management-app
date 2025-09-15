package com.dota.hero.manager.service;

import com.dota.hero.manager.dto.request.MatchHeroDto;
import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchService {

    MatchResponseDto createMatch(MatchRequestDto dto);

    MatchResponseDto attachHeroes(Long matchId, List<MatchHeroDto> matchHeroesDto);

    List<MatchHeroResponseDto> updateMatchHeroes(Long matchId, List<MatchHeroDto> matchHeroDtos);

    Page<MatchHeroResponseDto> getMatchHeroesByMatchId(Long matchId, Pageable pageable);

    List<Long> getMatches();

    void deleteMatchById(Long matchId);

}
