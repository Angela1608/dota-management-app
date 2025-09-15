package com.dota.hero.manager.dto.request;

import com.dota.hero.manager.util.ValidationConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.dota.hero.manager.util.ValidationConstants.DESCRIPTION_PATTERN;
import static com.dota.hero.manager.util.ValidationConstants.DESCRIPTION_PATTERN_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.MAX_DESCRIPTION_LENGTH;
import static com.dota.hero.manager.util.ValidationConstants.MIN_STAT_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.MIN_STAT_VALUE;
import static com.dota.hero.manager.util.ValidationConstants.NAME_PATTERN;
import static com.dota.hero.manager.util.ValidationConstants.NAME_PATTERN_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.NOT_BLANK_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.NOT_NULL_MESSAGE;
import static com.dota.hero.manager.util.ValidationConstants.SIZE_MESSAGE;

@Builder
public record ItemRequestDto(
        @NotBlank(message = "Item name " + NOT_BLANK_MESSAGE)
        @Pattern(regexp = NAME_PATTERN, message = "Item name " + NAME_PATTERN_MESSAGE)
        @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Item name " + SIZE_MESSAGE)
        String name,

        @NotNull(message = "Bonus strength " + NOT_NULL_MESSAGE)
        @Min(value = MIN_STAT_VALUE, message = "Bonus strength " + MIN_STAT_MESSAGE)
        Integer bonusStrength,

        @NotNull(message = "Bonus agility " + NOT_NULL_MESSAGE)
        @Min(value = MIN_STAT_VALUE, message = "Bonus agility " + MIN_STAT_MESSAGE)
        Integer bonusAgility,

        @NotNull(message = "Bonus intelligence " + NOT_NULL_MESSAGE)
        @Min(value = MIN_STAT_VALUE, message = "Bonus intelligence " + MIN_STAT_MESSAGE)
        Integer bonusIntelligence,

        @NotBlank(message = "Description " + NOT_BLANK_MESSAGE)
        @Pattern(regexp = DESCRIPTION_PATTERN, message = "Description " + DESCRIPTION_PATTERN_MESSAGE)
        @Size(max = MAX_DESCRIPTION_LENGTH, message = "Description " + SIZE_MESSAGE)
        String description
) {
}
