package com.dota.hero.manager.mapper;

import com.dota.hero.manager.dto.request.ItemRequestDto;
import com.dota.hero.manager.dto.response.ItemResponseDto;
import com.dota.hero.manager.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemRequestDto dto);

    ItemResponseDto toDto(Item entity);

}
