package com.dota.hero.manager.integration;


import com.dota.hero.manager.model.Hero;
import com.dota.hero.manager.repository.HeroRepository;
import com.dota.hero.manager.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static com.dota.hero.manager.util.TestDataUtil.createFirstHero;
import static com.dota.hero.manager.util.TestDataUtil.createSecondHero;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HeroControllerIntegrationTest extends AbstractionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtUser;

    @Autowired
    private HeroRepository heroRepository;


    @BeforeEach
    void setup() {
        heroRepository.deleteAll();
        heroRepository.save(createFirstHero());
        heroRepository.save(createSecondHero());
        jwtUser = TestDataUtil.getJwt("user");
    }

    @Test
    void whenGetAllHeroes_thenReturnFromDb() throws Exception {
        mockMvc.perform(get("/api/v1/heroes")
                        .with(jwtUser)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Dragon Knight")))
                .andExpect(jsonPath("$.content[0].primaryAttribute", is("STRENGTH")))
                .andExpect(jsonPath("$.content[0].baseMana", is(75)))
                .andExpect(jsonPath("$.content[0].baseHealth", is(200)))
                .andExpect(jsonPath("$.content[0].ultimateAbility", is("Elder Dragon Form")))
                .andExpect(jsonPath("$.content[1].name", is("Invoker")))
                .andExpect(jsonPath("$.content[1].primaryAttribute", is("INTELLIGENCE")))
                .andExpect(jsonPath("$.content[1].baseMana", is(75)))
                .andExpect(jsonPath("$.content[1].baseHealth", is(200)))
                .andExpect(jsonPath("$.content[1].ultimateAbility", is("Invoke")));
    }

    @Test
    void whenGetHeroById_thenReturnFromDb() throws Exception {
        Hero hero = heroRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/heroes/" + hero.getId())
                        .with(jwtUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(hero.getId().intValue())))
                .andExpect(jsonPath("$.name", is(hero.getName())))
                .andExpect(jsonPath("$.primaryAttribute", is(hero.getPrimaryAttribute().name())))
                .andExpect(jsonPath("$.baseMana", is(hero.getBaseMana())))
                .andExpect(jsonPath("$.baseHealth", is(hero.getBaseHealth())))
                .andExpect(jsonPath("$.ultimateAbility", is(hero.getUltimateAbility())));
    }

}
