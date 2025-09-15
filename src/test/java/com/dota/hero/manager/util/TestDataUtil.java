package com.dota.hero.manager.util;

import com.dota.hero.manager.dto.request.HeroRequestDto;
import com.dota.hero.manager.dto.request.ItemRequestDto;
import com.dota.hero.manager.dto.request.MatchHeroDto;
import com.dota.hero.manager.dto.request.MatchRequestDto;
import com.dota.hero.manager.dto.response.HeroResponseDto;
import com.dota.hero.manager.dto.response.ItemResponseDto;
import com.dota.hero.manager.dto.response.MatchHeroResponseDto;
import com.dota.hero.manager.dto.response.MatchResponseDto;
import com.dota.hero.manager.model.Hero;
import com.dota.hero.manager.model.Item;
import com.dota.hero.manager.model.Match;
import com.dota.hero.manager.model.MatchHero;
import com.dota.hero.manager.model.PrimaryAttribute;
import net.minidev.json.JSONArray;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor getJwt(String role) {
        return SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claims(claims -> {
                    JSONArray scopesArray = new JSONArray();
                    scopesArray.add("user");
                    claims.put("accountId", 1L);
                    claims.put("scope", scopesArray);
                    claims.put("given_name", "Jane");
                    claims.put("family_name", "Doe");
                    claims.put("role", role);
                    claims.put("email", "jane.doe@test.com");
                    claims.put("sub", "5230200a-05ca-41b6-a4f2-2b5531d1a80d");
                    claims.put("termsAndConditions", true);
                }));
    }

    public static Hero createFirstHero() {
        return Hero.builder()
                .name("Dragon Knight")
                .primaryAttribute(PrimaryAttribute.STRENGTH)
                .baseMana(75)
                .baseHealth(200)
                .ultimateAbility("Elder Dragon Form")
                .build();
    }

    public static Hero createSecondHero() {
        return Hero.builder()
                .name("Invoker")
                .primaryAttribute(PrimaryAttribute.INTELLIGENCE)
                .baseMana(75)
                .baseHealth(200)
                .ultimateAbility("Invoke")
                .build();
    }

    public static HeroResponseDto createHeroResponseDto() {
        return HeroResponseDto.builder()
                .id(1L)
                .name("Juggernaut")
                .primaryAttribute(PrimaryAttribute.STRENGTH)
                .baseMana(300)
                .baseHealth(600)
                .ultimateAbility("Omnislash")
                .build();
    }

    public static Item createFirstItem() {
        return Item.builder()
                .name("Black King Bar")
                .bonusStrength(10)
                .bonusAgility(0)
                .bonusIntelligence(0)
                .description("A powerful staff imbued with strength that grants spell immunity when activated.")
                .build();
    }

    public static Item createSecondItem() {
        return Item.builder()
                .name("Manta Style")
                .bonusStrength(10)
                .bonusAgility(26)
                .bonusIntelligence(10)
                .description("Creates two illusions of your hero to confuse enemies and dodge spells.")
                .build();
    }

    public static ItemResponseDto createItemResponseDto() {
        return ItemResponseDto.builder()
                .id(1L)
                .name("Manta Style")
                .bonusStrength(10)
                .bonusAgility(15)
                .bonusIntelligence(5)
                .description("Grants agility and strength bonuses")
                .build();
    }

    public static MatchRequestDto createFirstMatchRequestDto() {
        HeroRequestDto juggernaut = HeroRequestDto.builder()
                .name("Juggernaut")
                .primaryAttribute(PrimaryAttribute.STRENGTH)
                .baseMana(300)
                .baseHealth(600)
                .ultimateAbility("Omnislash")
                .build();

        HeroRequestDto lina = HeroRequestDto.builder()
                .name("Lina")
                .primaryAttribute(PrimaryAttribute.INTELLIGENCE)
                .baseMana(500)
                .baseHealth(400)
                .ultimateAbility("Laguna Blade")
                .build();

        ItemRequestDto manta = ItemRequestDto.builder()
                .name("Manta Style")
                .bonusStrength(10)
                .bonusAgility(26)
                .bonusIntelligence(10)
                .description("Creates two illusions of your hero to confuse enemies and dodge spells.")
                .build();

        ItemRequestDto euls = ItemRequestDto.builder()
                .name("Eul's Scepter")
                .bonusStrength(5)
                .bonusAgility(5)
                .bonusIntelligence(35)
                .description("Cyclone an enemy or yourself into the air.")
                .build();

        MatchHeroDto juggernautInMatch = MatchHeroDto.builder()
                .heroRequestDto(juggernaut)
                .itemsRequestDto(List.of(manta))
                .build();

        MatchHeroDto linaInMatch = MatchHeroDto.builder()
                .heroRequestDto(lina)
                .itemsRequestDto(List.of(euls))
                .build();

        return MatchRequestDto.builder()
                .matchHeroes(List.of(juggernautInMatch, linaInMatch))
                .build();
    }

    public static MatchRequestDto createSecondMatchRequestDto() {
        HeroRequestDto juggernaut = HeroRequestDto.builder()
                .name("Juggernaut")
                .primaryAttribute(PrimaryAttribute.AGILITY)
                .baseMana(300)
                .baseHealth(600)
                .ultimateAbility("Omnislash")
                .build();

        HeroRequestDto lina = HeroRequestDto.builder()
                .name("Lina")
                .primaryAttribute(PrimaryAttribute.INTELLIGENCE)
                .baseMana(600)
                .baseHealth(500)
                .ultimateAbility("Laguna Blade")
                .build();

        ItemRequestDto manta = ItemRequestDto.builder()
                .name("Manta Style")
                .bonusStrength(10)
                .bonusAgility(26)
                .bonusIntelligence(10)
                .description("Creates two illusions of your hero to confuse enemies and dodge spells.")
                .build();

        ItemRequestDto eul = ItemRequestDto.builder()
                .name("Eul's Scepter")
                .bonusStrength(0)
                .bonusAgility(0)
                .bonusIntelligence(10)
                .description("Cyclone yourself or an enemy for a short time.")
                .build();

        MatchHeroDto juggernautInMatch = MatchHeroDto.builder()
                .heroRequestDto(juggernaut)
                .itemsRequestDto(List.of(manta))
                .build();

        MatchHeroDto linaInMatch = MatchHeroDto.builder()
                .heroRequestDto(lina)
                .itemsRequestDto(List.of(eul))
                .build();

        return MatchRequestDto.builder()
                .matchHeroes(List.of(juggernautInMatch, linaInMatch))
                .build();
    }

    public static Match createFirstMatch() {
        Match match = Match.builder().build();

        MatchHero mh1 = MatchHero.builder()
                .hero(createFirstHero())
                .items(new ArrayList<>(List.of(createFirstItem())))
                .build();

        MatchHero mh2 = MatchHero.builder()
                .hero(createSecondHero())
                .items(new ArrayList<>(List.of(createSecondItem())))
                .build();

        match.setMatchHeroes(new ArrayList<>(List.of(mh1, mh2)));
        return match;
    }

    public static Match createSecondMatch() {
        Match match = Match.builder().build();

        Hero juggernaut = Hero.builder()
                .name("Juggernaut")
                .primaryAttribute(PrimaryAttribute.AGILITY)
                .baseHealth(600)
                .baseMana(300)
                .ultimateAbility("Omnislash")
                .build();

        Hero lina = Hero.builder()
                .name("Lina")
                .primaryAttribute(PrimaryAttribute.INTELLIGENCE)
                .baseHealth(500)
                .baseMana(600)
                .ultimateAbility("Laguna Blade")
                .build();

        Item manta = createSecondItem();
        Item eul = Item.builder()
                .name("Eul's Scepter")
                .bonusStrength(0)
                .bonusAgility(0)
                .bonusIntelligence(10)
                .description("Cyclone yourself or an enemy for a short time.")
                .build();

        MatchHero mh1 = MatchHero.builder()
                .match(match)
                .hero(juggernaut)
                .items(new ArrayList<>(List.of(manta)))
                .build();

        MatchHero mh2 = MatchHero.builder()
                .match(match)
                .hero(lina)
                .items(new ArrayList<>(List.of(eul)))
                .build();

        match.setMatchHeroes(new ArrayList<>(List.of(mh1, mh2)));
        return match;
    }

    public static MatchResponseDto createMatchResponseDto() {
        HeroResponseDto hero = createHeroResponseDto();

        ItemResponseDto manta = ItemResponseDto.builder()
                .id(1L)
                .name("Manta Style")
                .bonusStrength(10)
                .bonusAgility(25)
                .bonusIntelligence(12)
                .description("Creates illusions to confuse enemies")
                .build();

        MatchHeroResponseDto matchHero = MatchHeroResponseDto.builder()
                .id(1L)
                .hero(hero)
                .items(List.of(manta))
                .totalHealth(1600)
                .totalMana(600)
                .totalStrength(25)
                .totalIntelligence(20)
                .totalAgility(30)
                .build();

        return MatchResponseDto.builder()
                .id(100L)
                .matchHeroes(List.of(matchHero))
                .build();
    }

}
