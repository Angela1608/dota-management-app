package com.dota.hero.manager.dto.response;

import com.dota.hero.manager.model.PrimaryAttribute;
import lombok.Builder;

@Builder
public record HeroResponseDto(
        Long id,
        String name,
        PrimaryAttribute primaryAttribute,
        Integer baseMana,
        Integer baseHealth,
        String ultimateAbility
) {
}
