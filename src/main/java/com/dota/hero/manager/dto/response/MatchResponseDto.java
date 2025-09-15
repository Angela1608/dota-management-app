package com.dota.hero.manager.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record MatchResponseDto(
        Long id,
        List<MatchHeroResponseDto> matchHeroes
) {
}
