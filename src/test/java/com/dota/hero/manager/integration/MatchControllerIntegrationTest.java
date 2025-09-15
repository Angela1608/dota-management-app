package com.dota.hero.manager.integration;

import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import com.dota.hero.manager.repository.MatchRepository;
import com.dota.hero.manager.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MatchControllerIntegrationTest extends AbstractionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepository matchRepository;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtUser;
    private MatchRequestDto matchRequestDto;

    @BeforeEach
    void setup() {
        jwtUser = TestDataUtil.getJwt("user");
        matchRequestDto = TestDataUtil.createFirstMatchRequestDto();
        matchRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void whenCreateMatch_thenReturnMatchResponseDto() {
        mockMvc.perform(post("/api/v1/matches")
                        .with(jwtUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.matchHeroes[0].hero.name", is("Juggernaut")))
                .andExpect(jsonPath("$.matchHeroes[0].hero.primaryAttribute", is("AGILITY")))
                .andExpect(jsonPath("$.matchHeroes[0].items[0].name", is("Manta Style")))
                .andExpect(jsonPath("$.matchHeroes[1].hero.name", is("Lina")))
                .andExpect(jsonPath("$.matchHeroes[1].hero.primaryAttribute", is("INTELLIGENCE")))
                .andExpect(jsonPath("$.matchHeroes[1].items[0].name", is("Eul's Scepter")));
    }

    @Test
    @SneakyThrows
    void whenAttachHeroes_thenReturnMatchResponseDto() {
        MatchResponseDto createdMatch = createMatch();

        mockMvc.perform(post("/api/v1/matches/{matchId}/heroes", createdMatch.id())
                        .with(jwtUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchRequestDto.matchHeroes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdMatch.id().intValue())))
                .andExpect(jsonPath("$.matchHeroes", hasSize(2)))
                .andExpect(jsonPath("$.matchHeroes[0].hero.name", is("Juggernaut")))
                .andExpect(jsonPath("$.matchHeroes[1].hero.name", is("Lina")));
    }

    @Test
    @SneakyThrows
    void whenUpdateHeroes_thenReturnUpdatedHeroes() {
        MatchResponseDto createdMatch = createMatch();

        mockMvc.perform(put("/api/v1/matches/{matchId}/heroes", createdMatch.id())
                        .with(jwtUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchRequestDto.matchHeroes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].hero.primaryAttribute", is("AGILITY")))
                .andExpect(jsonPath("$[1].hero.baseMana", is(75)));
    }

    @Test
    @SneakyThrows
    void whenGetHeroesByMatchId_thenReturnHeroesPage() {
        MatchResponseDto createdMatch = createMatch();

        mockMvc.perform(get("/api/v1/matches/{matchId}/heroes", createdMatch.id())
                        .with(jwtUser)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].hero.name", is("Juggernaut")));
    }

    @Test
    @SneakyThrows
    void whenGetMatches_thenReturnListOfMatchIds() {
        createMatch();
        mockMvc.perform(get("/api/v1/matches")
                        .with(jwtUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void whenDeleteMatch_thenReturnNoContent() {
        MatchResponseDto createdMatch = createMatch();

        mockMvc.perform(delete("/api/v1/matches/{matchId}", createdMatch.id())
                        .with(jwtUser))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    private MatchResponseDto createMatch() {
        String responseDto = mockMvc.perform(post("/api/v1/matches")
                        .with(jwtUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchRequestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseDto, MatchResponseDto.class);
    }

}
