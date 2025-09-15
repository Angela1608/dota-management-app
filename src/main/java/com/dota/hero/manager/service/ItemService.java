package com.dota.hero.manager.service;

import com.dota.hero.manager.dto.response.ItemResponseDto;
import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.mapper.ItemMapper;
import com.dota.hero.manager.model.Item;
import com.dota.hero.manager.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Cacheable(value = "items", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public Page<ItemResponseDto> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
    }

    public ItemResponseDto getById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Item with id [%d] not found", id)));

        return itemMapper.toDto(item);
    }

}
