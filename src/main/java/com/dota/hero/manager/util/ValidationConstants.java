package com.dota.hero.manager.util;

public final class ValidationConstants {

    private ValidationConstants() {
    }

    public static final String NAME_PATTERN = "^[A-Za-z0-9\\s'-]+$";
    public static final String NAME_PATTERN_MESSAGE = "must contain only letters, numbers, spaces, hyphens, or apostrophes";

    public static final String DESCRIPTION_PATTERN = "^[A-Za-z0-9\\s\\-',.!?()]+$";
    public static final String DESCRIPTION_PATTERN_MESSAGE = "must contain only letters, numbers, spaces, and basic punctuation";

    public static final int MIN_STAT_VALUE = 0;
    public static final String MIN_STAT_MESSAGE = "must be non-negative";

    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 255;
    public static final int MAX_ULTIMATE_ABILITY_LENGTH = 100;
    public static final int MAX_HERO_AMOUNT = 6;

    public static final String NOT_BLANK_MESSAGE = "cannot be empty";
    public static final String NOT_NULL_MESSAGE = "cannot be null";
    public static final String SIZE_MESSAGE = "must not exceed {max} characters";
    public static final String MATCH_HERO_SIZE_MESSAGE = "Cannot have more than 6 heroes in a match";

}
