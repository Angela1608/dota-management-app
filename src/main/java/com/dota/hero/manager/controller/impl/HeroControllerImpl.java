package com.dota.hero.manager.controller.impl;

import com.dota.hero.manager.controller.HeroController;
import com.dota.hero.manager.dto.response.HeroResponseDto;
import com.dota.hero.manager.service.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeroControllerImpl implements HeroController {

    private final HeroService heroService;

    @Override
    public ResponseEntity<Page<HeroResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(heroService.getAll(pageable));
    }

    @Override
    public ResponseEntity<HeroResponseDto> getHeroById(Long id) {
        return ResponseEntity.ok(heroService.getById(id));
    }

}
