package com.dota.hero.manager.controller.impl;

import com.dota.hero.manager.controller.MatchController;
import com.dota.hero.manager.dto.request.MatchHeroDto;
import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import com.dota.hero.manager.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchControllerImpl implements MatchController {

    private final MatchService matchService;

    @Override
    public ResponseEntity<MatchResponseDto> createMatch(MatchRequestDto matchRequestDto) {
        return ResponseEntity.ok(matchService.createMatch(matchRequestDto));
    }

    @Override
    public ResponseEntity<MatchResponseDto> attachHeroes(Long matchId, List<MatchHeroDto> matchHeroesDto) {
        return ResponseEntity.ok(matchService.attachHeroes(matchId, matchHeroesDto));
    }

    @Override
    public ResponseEntity<List<MatchHeroResponseDto>> updateHeroes(Long matchId, List<MatchHeroDto> matchHeroDtos) {
        return ResponseEntity.ok(matchService.updateMatchHeroes(matchId, matchHeroDtos));
    }

    @Override
    public ResponseEntity<Page<MatchHeroResponseDto>> getHeroesByMatchId(Long matchId, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchHeroesByMatchId(matchId, pageable));
    }

    @Override
    public ResponseEntity<List<Long>> getMatches() {
        return ResponseEntity.ok(matchService.getMatches());
    }

    @Override
    public ResponseEntity<Void> deleteMatchById(Long matchId) {
        matchService.deleteMatchById(matchId);
        return ResponseEntity.noContent().build();
    }

}
