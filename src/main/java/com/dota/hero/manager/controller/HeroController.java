package com.dota.hero.manager.controller;

import com.dota.hero.manager.dto.response.HeroResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/heroes")
public interface HeroController {

    @Operation(summary = "Retrieve all heroes",
            description = "Returns a list of all heroes from the dictionary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "No heroes found")
    })
    @GetMapping(name = "GET_getHeroes")
    ResponseEntity<Page<HeroResponseDto>> getAll(Pageable pageable);

    @Operation(summary = "Retrieve hero by ID",
            description = "Returns hero details by the given identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hero found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Hero not found")
    })
    @GetMapping(value = "/{id}", name = "GET_getHeroById")
    ResponseEntity<HeroResponseDto> getHeroById(@PathVariable Long id);

}
