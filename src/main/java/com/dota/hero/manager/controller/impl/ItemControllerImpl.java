package com.dota.hero.manager.controller.impl;

import com.dota.hero.manager.controller.ItemController;
import com.dota.hero.manager.dto.response.ItemResponseDto;
import com.dota.hero.manager.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemControllerImpl implements ItemController {

    private final ItemService itemService;

    @Override
    public ResponseEntity<Page<ItemResponseDto>> getItems(Pageable pageable) {

        return ResponseEntity.ok(itemService.getAll(pageable));
    }

    @Override
    public ResponseEntity<ItemResponseDto> getItemById(Long id) {

        return ResponseEntity.ok(itemService.getById(id));
    }

}
