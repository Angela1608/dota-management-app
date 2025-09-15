package com.dota.hero.manager.controller;

import com.dota.hero.manager.dto.response.ItemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/items")
public interface ItemController {

    @Operation(summary = "Retrieve all items",
            description = "Returns a list of all items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "No items found")
    })
    @GetMapping(name = "GET_getItems")
    ResponseEntity<Page<ItemResponseDto>> getItems(Pageable pageable);

    @Operation(summary = "Retrieve item by ID",
            description = "Returns item details by the given identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping(value = "/{id}", name = "GET_getItemById")
    ResponseEntity<ItemResponseDto> getItemById(@PathVariable Long id);

}
