package com.dota.hero.manager.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MatchHeroResponseDto(
        Long id,
        HeroResponseDto hero,
        List<ItemResponseDto> items,
        Integer totalHealth,
        Integer totalMana,
        Integer totalStrength,
        Integer totalIntelligence,
        Integer totalAgility
) {
}