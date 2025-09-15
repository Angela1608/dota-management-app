package com.dota.hero.manager.integration;

import com.dota.hero.manager.model.Item;
import com.dota.hero.manager.repository.ItemRepository;
import com.dota.hero.manager.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static com.dota.hero.manager.util.TestDataUtil.createFirstItem;
import static com.dota.hero.manager.util.TestDataUtil.createSecondItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerIntegrationTest extends AbstractionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtUser;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();
        itemRepository.save(createFirstItem());
        itemRepository.save(createSecondItem());
        jwtUser = TestDataUtil.getJwt("user");
    }

    @Test
    void whenGetAllItems_thenReturnFromDb() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .with(jwtUser)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Black King Bar")))
                .andExpect(jsonPath("$.content[0].bonusStrength", is(10)))
                .andExpect(jsonPath("$.content[0].bonusAgility", is(0)))
                .andExpect(jsonPath("$.content[0].bonusIntelligence", is(0)))
                .andExpect(jsonPath("$.content[0].description", is("A powerful staff imbued with strength that grants spell immunity when activated.")))
                .andExpect(jsonPath("$.content[1].name", is("Manta Style")))
                .andExpect(jsonPath("$.content[1].bonusStrength", is(10)))
                .andExpect(jsonPath("$.content[1].bonusAgility", is(26)))
                .andExpect(jsonPath("$.content[1].bonusIntelligence", is(10)))
                .andExpect(jsonPath("$.content[1].description", is("Creates two illusions of your hero to confuse enemies and dodge spells.")));
    }

    @Test
    void whenGetItemById_thenReturnFromDb() throws Exception {
        Item item = itemRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/items/" + item.getId())
                        .with(jwtUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId().intValue())))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.bonusStrength", is(item.getBonusStrength())))
                .andExpect(jsonPath("$.bonusAgility", is(item.getBonusAgility())))
                .andExpect(jsonPath("$.bonusIntelligence", is(item.getBonusIntelligence())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

}
