package com.dota.hero.manager.unit;

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
import com.dota.hero.manager.service.impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.dota.hero.manager.util.TestDataUtil.createFirstHero;
import static com.dota.hero.manager.util.TestDataUtil.createFirstItem;
import static com.dota.hero.manager.util.TestDataUtil.createFirstMatch;
import static com.dota.hero.manager.util.TestDataUtil.createFirstMatchRequestDto;
import static com.dota.hero.manager.util.TestDataUtil.createHeroResponseDto;
import static com.dota.hero.manager.util.TestDataUtil.createItemResponseDto;
import static com.dota.hero.manager.util.TestDataUtil.createMatchResponseDto;
import static com.dota.hero.manager.util.TestDataUtil.createSecondHero;
import static com.dota.hero.manager.util.TestDataUtil.createSecondItem;
import static com.dota.hero.manager.util.TestDataUtil.createSecondMatch;
import static com.dota.hero.manager.util.TestDataUtil.createSecondMatchRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private MatchHeroRepository matchHeroRepository;

    @Mock
    private MatchMapper matchMapper;

    @Mock
    private HeroMapper heroMapper;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Match match;
    private MatchResponseDto matchResponseDto;
    private MatchHero matchHero;
    private MatchHeroResponseDto matchHeroResponseDto;

    @BeforeEach
    void setUp() {
        match = createFirstMatch();
        match.setId(1L);

        matchHero = MatchHero.builder()
                .id(1L)
                .match(match)
                .hero(createFirstHero())
                .items(List.of(createFirstItem()))
                .build();

        matchResponseDto = createMatchResponseDto();
        matchHeroResponseDto = MatchHeroResponseDto.builder()
                .id(1L)
                .hero(createHeroResponseDto())
                .items(List.of(createItemResponseDto()))
                .build();
    }

    @Test
    void whenCreateMatch_thenReturnMatchResponseDto() {
        MatchRequestDto matchRequestDto = createFirstMatchRequestDto();

        when(matchRepository.save(ArgumentMatchers.any(Match.class))).thenReturn(match);
        when(heroRepository.findByName(anyString())).thenReturn(null);
        when(heroMapper.toEntity(any())).thenReturn(createFirstHero());
        when(heroRepository.save(any(Hero.class))).thenReturn(createFirstHero());
        when(itemRepository.findByName(anyString())).thenReturn(null);
        when(itemMapper.toEntity(any())).thenReturn(createFirstItem());
        when(itemRepository.save(any(Item.class))).thenReturn(createFirstItem());
        when(matchHeroRepository.save(any(MatchHero.class))).thenReturn(matchHero);
        when(matchMapper.toDto(match)).thenReturn(matchResponseDto);

        MatchResponseDto result = matchService.createMatch(matchRequestDto);

        assertNotNull(result);
        assertEquals(100L, result.id());
        verify(matchRepository, times(2)).save(any(Match.class));
        verify(matchHeroRepository, times(2)).save(any(MatchHero.class));
    }

    @Test
    void whenAttachHeroes_andMatchExists_thenReturnMatchResponseDto() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchMapper.toDto(match)).thenReturn(matchResponseDto);
        when(matchRepository.save(match)).thenReturn(match);
        when(matchHeroRepository.save(any(MatchHero.class))).thenReturn(matchHero);
        when(heroRepository.findByName(anyString())).thenReturn(null);
        when(heroMapper.toEntity(any())).thenReturn(createSecondHero());
        when(heroRepository.save(any(Hero.class))).thenReturn(createSecondHero());
        when(itemRepository.findByName(anyString())).thenReturn(null);
        when(itemMapper.toEntity(any())).thenReturn(createSecondItem());
        when(itemRepository.save(any(Item.class))).thenReturn(createSecondItem());

        MatchRequestDto request = createSecondMatchRequestDto();

        MatchResponseDto result = matchService.attachHeroes(1L, request.matchHeroes());

        assertNotNull(result);
        assertEquals(100L, result.id());
        verify(matchRepository, times(1)).findById(1L);
    }

    @Test
    void whenAttachHeroes_andMatchDoesNotExist_thenThrowNotFoundException() {
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                matchService.attachHeroes(1L, createSecondMatchRequestDto().matchHeroes())
        );

        assertEquals("Match with id [1] not found", exception.getMessage());
    }

    @Test
    void whenGetMatches_thenReturnListOfIds() {
        Match match2 = createSecondMatch();
        match2.setId(2L);

        when(matchRepository.findAll()).thenReturn(List.of(match, match2));

        var result = matchService.getMatches();

        assertEquals(List.of(1L, 2L), result);
    }

    @Test
    void whenDeleteMatchById_andMatchExists_thenDeleteMatch() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        matchService.deleteMatchById(1L);

        verify(matchRepository, times(1)).delete(match);
    }

    @Test
    void whenDeleteMatchById_andMatchDoesNotExist_thenThrowNotFoundException() {
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                matchService.deleteMatchById(1L));

        assertEquals("Match with id [1] not found", exception.getMessage());
        verify(matchRepository, never()).delete(any());
    }

}
