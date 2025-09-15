package com.dota.hero.manager.dto.response;

import lombok.Builder;

@Builder
public record ItemResponseDto(
        Long id,
        String name,
        Integer bonusStrength,
        Integer bonusAgility,
        Integer bonusIntelligence,
        String description
) {
}
