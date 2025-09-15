package com.dota.hero.manager.dto.request;

import com.dota.hero.manager.model.PrimaryAttribute;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.dota.hero.manager.util.ValidationConstants.MAX_NAME_LENGTH;
import static com.dota.hero.manager.util.ValidationConstants.MAX_ULTIMATE_ABILITY_LENGTH;
import static com.dota.hero.manager.util.ValidationConstants.MIN_STAT_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.MIN_STAT_VALUE;
import static com.dota.hero.manager.util.ValidationConstants.NAME_PATTERN;
import static com.dota.hero.manager.util.ValidationConstants.NAME_PATTERN_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.NOT_BLANK_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.NOT_NULL_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.SIZE_MESSAGE;

@Builder
public record HeroRequestDto(
        @NotBlank(message = "Hero name " + NOT_BLANK_MESSAGE)
        @Pattern(regexp = NAME_PATTERN, message = "Hero name " + NAME_PATTERN_MESSAGE)
        @Size(max = MAX_NAME_LENGTH, message = "Hero name " + SIZE_MESSAGE)
        String name,

        @NotNull(message = "Primary attribute " + NOT_NULL_MESSAGE)
        PrimaryAttribute primaryAttribute,

        @NotNull(message = "Base mana " + NOT_NULL_MESSAGE)
        @Min(value = MIN_STAT_VALUE, message = "Base mana " + MIN_STAT_MESSAGE)
        Integer baseMana,

        @NotNull(message = "Base health " + NOT_NULL_MESSAGE)
        @Min(value = MIN_STAT_VALUE, message = "Base health " + MIN_STAT_MESSAGE)
        Integer baseHealth,

        @NotBlank(message = "Ultimate ability " + NOT_BLANK_MESSAGE)
        @Pattern(regexp = NAME_PATTERN, message = "Ultimate ability " + NAME_PATTERN_MESSAGE)
        @Size(max = MAX_ULTIMATE_ABILITY_LENGTH, message = "Ultimate ability " + SIZE_MESSAGE)
        String ultimateAbility
) {
}
