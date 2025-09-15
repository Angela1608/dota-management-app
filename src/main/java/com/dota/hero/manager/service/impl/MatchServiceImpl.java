package com.dota.hero.manager.service.impl;

import com.dota.hero.manager.dto.request.HeroRequestDto;
import com.dota.hero.manager.dto.request.ItemRequestDto;
import com.dota.hero.manager.dto.request.MatchHeroDto;
import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.mapper.HeroMapper;
import com.dota.hero.manager.mapper.ItemMapper;
import com.dota.hero.manager.mapper.MatchMapper;
import com.dota.hero.manager.model.Hero;
import com.dota.hero.manager.model.Item;
import com.dota.hero.manager.model.Match;
import com.dota.hero.manager.model.MatchHero;
import com.dota.hero.manager.repository.HeroRepository;
import com.dota.hero.manager.repository.ItemRepository;
import com.dota.hero.manager.repository.MatchHeroRepository;
import com.dota.hero.manager.repository.MatchRepository;
import com.dota.hero.manager.service.MatchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;
    private final MatchHeroRepository matchHeroRepository;
    private final MatchMapper matchMapper;
    private final HeroMapper heroMapper;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public MatchResponseDto createMatch(MatchRequestDto matchRequestDto) {
        Match match = new Match();
        Match savedMatch = matchRepository.save(match);

        List<MatchHero> matchHeroes = matchRequestDto.matchHeroes().stream()
                .map(matchHeroDto -> createMatchHero(savedMatch, matchHeroDto))
                .collect(Collectors.toList());

        savedMatch.setMatchHeroes(matchHeroes);
        return matchMapper.toDto(matchRepository.save(savedMatch));
    }

    @Override
    @Transactional
    public MatchResponseDto attachHeroes(Long matchId, List<MatchHeroDto> matchHeroesDto) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("Match with id [%d] not found".formatted(matchId)));

        match.getMatchHeroes().clear();

        matchHeroesDto.stream()
                .map(matchHeroDto -> createMatchHero(match, matchHeroDto))
                .forEach(match.getMatchHeroes()::add);

        return matchMapper.toDto(matchRepository.save(match));
    }

    @Override
    @Transactional
    public List<MatchHeroResponseDto> updateMatchHeroes(Long matchId, List<MatchHeroDto> matchHeroDtos) {
        MatchResponseDto matchResponse = attachHeroes(matchId, matchHeroDtos);

        return matchResponse.matchHeroes();
    }

    @Override
    @Transactional
    public Page<MatchHeroResponseDto> getMatchHeroesByMatchId(Long matchId, Pageable pageable) {
        Page<MatchHero> matchHeroes = matchHeroRepository.findMatchHeroesByMatch_Id(matchId, pageable);
        return matchHeroes.map(matchMapper::toDto);
    }

    @Override
    public List<Long> getMatches() {
        return matchRepository.findAll().stream().map(Match::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMatchById(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("Match with id [%d] not found".formatted(matchId)));

        matchRepository.delete(match);
    }

    private MatchHero createMatchHero(Match match, MatchHeroDto matchHeroDto) {
        Hero hero = getOrCreateHero(matchHeroDto.heroRequestDto());
        List<Item> items = getOrCreateItems(matchHeroDto.itemsRequestDto());

        MatchHero matchHero = new MatchHero();
        matchHero.setMatch(match);
        matchHero.setHero(hero);
        matchHero.setItems(items);

        return matchHeroRepository.save(matchHero);
    }

    private Hero getOrCreateHero(HeroRequestDto heroRequestDto) {
        return Optional.ofNullable(heroRepository.findByName(heroRequestDto.name()))
                .orElseGet(() -> heroRepository.save(heroMapper.toEntity(heroRequestDto)));
    }

    private List<Item> getOrCreateItems(List<ItemRequestDto> itemsRequestDto) {
        return itemsRequestDto.stream()
                .map(this::getOrCreateItem)
                .collect(Collectors.toList());
    }

    private Item getOrCreateItem(ItemRequestDto itemRequestDto) {
        return Optional.ofNullable(itemRepository.findByName(itemRequestDto.name()))
                .orElseGet(() -> itemRepository.save(itemMapper.toEntity(itemRequestDto)));
    }

}
