package com.dota.hero.manager.unit;

import com.dota.hero.manager.dto.response.ItemResponseDto;
import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.mapper.ItemMapper;
import com.dota.hero.manager.model.Item;
import com.dota.hero.manager.repository.ItemRepository;
import com.dota.hero.manager.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dota.hero.manager.util.TestDataUtil.createFirstItem;
import static com.dota.hero.manager.util.TestDataUtil.createItemResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemResponseDto itemResponseDto;

    @BeforeEach
    void setUp() {
        item = createFirstItem();
        itemResponseDto = createItemResponseDto();
    }

    @Test
    void whenGetAll_thenReturnPageOfItemResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(item));

        when(itemRepository.findAll(pageable)).thenReturn(itemPage);
        when(itemMapper.toDto(item)).thenReturn(itemResponseDto);

        Page<ItemResponseDto> result = itemService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(itemRepository, times(1)).findAll(pageable);
        verify(itemMapper, times(1)).toDto(item);
    }

    @Test
    void whenGetById_andItemExists_thenReturnItemResponseDto() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemMapper.toDto(item)).thenReturn(itemResponseDto);

        ItemResponseDto result = itemService.getById(1L);

        assertNotNull(result);
        verify(itemRepository, times(1)).findById(1L);
        verify(itemMapper, times(1)).toDto(item);
    }

    @Test
    void whenGetById_andItemDoesNotExist_thenThrowNotFoundException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> itemService.getById(1L));

        assertEquals("Item with id [1] not found", exception.getMessage());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemMapper, never()).toDto(ArgumentMatchers.any());
    }

}
