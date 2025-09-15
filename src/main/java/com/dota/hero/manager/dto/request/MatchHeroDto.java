package com.dota.hero.manager.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record MatchHeroDto(HeroRequestDto heroRequestDto,
                           List<ItemRequestDto> itemsRequestDto) {
}
