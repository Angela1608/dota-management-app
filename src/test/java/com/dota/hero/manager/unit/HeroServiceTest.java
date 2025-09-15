package com.dota.hero.manager.unit;

import com.dota.hero.manager.dto.response.HeroResponseDto;
import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.mapper.HeroMapper;
import com.dota.hero.manager.model.Hero;
import com.dota.hero.manager.repository.HeroRepository;
import com.dota.hero.manager.service.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dota.hero.manager.util.TestDataUtil.createFirstHero;
import static com.dota.hero.manager.util.TestDataUtil.createHeroResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private HeroMapper heroMapper;

    @InjectMocks
    private HeroService heroService;

    private Hero hero;
    private HeroResponseDto heroResponseDto;

    @BeforeEach
    void setUp() {
        hero = createFirstHero();
        heroResponseDto = createHeroResponseDto();
    }

    @Test
    void whenGetAll_thenReturnPageOfHeroResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hero> heroPage = new PageImpl<>(List.of(hero));

        when(heroRepository.findAll(pageable)).thenReturn(heroPage);
        when(heroMapper.toDto(hero)).thenReturn(heroResponseDto);

        Page<HeroResponseDto> result = heroService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(heroRepository, times(1)).findAll(pageable);
        verify(heroMapper, times(1)).toDto(hero);
    }

    @Test
    void whenGetById_andHeroExists_thenReturnHeroResponseDto() {
        when(heroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(heroMapper.toDto(hero)).thenReturn(heroResponseDto);

        HeroResponseDto result = heroService.getById(1L);

        assertNotNull(result);
        verify(heroRepository, times(1)).findById(1L);
        verify(heroMapper, times(1)).toDto(hero);
    }

    @Test
    void whenGetById_andHeroDoesNotExist_thenThrowNotFoundException() {
        when(heroRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> heroService.getById(1L));

        assertEquals("Hero with id [1] not found", exception.getMessage());
        verify(heroRepository, times(1)).findById(1L);
        verify(heroMapper, never()).toDto(ArgumentMatchers.any());
    }

}
