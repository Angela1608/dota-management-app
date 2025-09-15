package com.dota.hero.manager.controller;

import com.dota.hero.manager.dto.request.MatchHeroDto;
import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/matches")
@Validated
public interface MatchController {

    @Operation(summary = "Create match (lobby)",
            description = "Creates a new match (lobby) with a unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied")
    })
    @PostMapping
    ResponseEntity<MatchResponseDto> createMatch(@Valid @RequestBody MatchRequestDto matchRequestDto);

    @Operation(summary = "Attach heroes to match",
            description = "Attaches up to 6 heroes with items to a match by match ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Heroes attached successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Match or hero not found")
    })
    @PostMapping("/{matchId}/heroes")
    ResponseEntity<MatchResponseDto> attachHeroes(
            @PathVariable Long matchId,
            @Valid @RequestBody List<MatchHeroDto> matchHeroesDto
    );

    @Operation(summary = "Update heroes in match",
            description = "Edits the list of heroes with items in an existing match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Heroes updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "Match or hero not found")
    })
    @PutMapping("/{matchId}/heroes")
    ResponseEntity<List<MatchHeroResponseDto>> updateHeroes(
            @PathVariable Long matchId,
            @RequestBody List<MatchHeroDto> matchHeroDtos
    );

    @Operation(summary = "Get heroes in match",
            description = "Returns a list of all heroes with attached items in a match by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @GetMapping("/{matchId}/heroes")
    ResponseEntity<Page<MatchHeroResponseDto>> getHeroesByMatchId(@PathVariable Long matchId, Pageable pageable);

    @Operation(summary = "Get all matches",
            description = "Returns a list of all existing matches (lobbies).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    })
    @GetMapping
    ResponseEntity<List<Long>> getMatches();

    @Operation(summary = "Delete match",
            description = "Deletes a match (lobby) by the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @DeleteMapping("/{matchId}")
    ResponseEntity<Void> deleteMatchById(@PathVariable Long matchId);

}
