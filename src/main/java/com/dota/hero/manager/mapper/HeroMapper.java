package com.dota.hero.manager.mapper;

import com.dota.hero.manager.dto.request.HeroRequestDto;
import com.dota.hero.manager.dto.response.HeroResponseDto;
import com.dota.hero.manager.model.Hero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    Hero toEntity(HeroRequestDto dto);

    HeroResponseDto toDto(Hero entity);

}
