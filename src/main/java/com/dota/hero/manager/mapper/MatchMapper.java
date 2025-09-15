package com.dota.hero.manager.mapper;

import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import com.dota.hero.manager.model.Match;
import com.dota.hero.manager.model.MatchHero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    MatchResponseDto toDto(Match entity);

    MatchHeroResponseDto toDto(MatchHero entity);

}

