package com.dota.hero.manager.service;

import com.dota.hero.manager.dto.response.HeroResponseDto;
import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.mapper.HeroMapper;
import com.dota.hero.manager.model.Hero;
import com.dota.hero.manager.repository.HeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;

    @Cacheable(value = "heroes", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public Page<HeroResponseDto> getAll(Pageable pageable) {
        return heroRepository.findAll(pageable).map(heroMapper::toDto);
    }

    public HeroResponseDto getById(Long id) {
        Hero hero = heroRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Hero with id [%d] not found", id)));

        return heroMapper.toDto(hero);
    }

}
