package com.dota.hero.manager.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

import static com.dota.hero.manager.util.ValidationConstants.MATCH_HERO_SIZE_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.MAX_HERO_AMOUNT;

@Builder
public record MatchRequestDto(@Size(max = MAX_HERO_AMOUNT, message = MATCH_HERO_SIZE_MESSAGE)
                              List<MatchHeroDto> matchHeroes) {
}
