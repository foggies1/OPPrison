package net.prison.foggies.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Lang {

    PREFIX("&4&lVERITY ▎ "),
    NOT_ENOUGH_MONEY("&cYou do not have enough money."),
    MINE_ADDED_TO_QUEUE("&7Your Mine has been added to the queue to reset!"),
    MINE_BLOCK_CHANGED("&7You've changed your Mine Block to &c%block%&7!"),
    REACHED_MAX_MINE_SIZE("&7You've reached the Mines maximum size!"),
    MINE_SIZE_INCREASED("&7You've increased the size of your Mine by &c%amount%&7!"),
    MINE_CREATED("&7Your Mine has successfully been created, use &c/mine tp &7to go there."),
    GREATER_THAN_0("&cThe amount you entered is invalid, please enter an amount greater than 0."),
    GREATER_THAN_OR_EQUAL_TO_0("&cThe amount you entered is invalid, please enter an amount greater than or equal to 0."),
    BLOCK_SYMBOL("▎ "),
    MAX_LEVEL_REACHED("&7You've reached maximum level, use &c/prestige&7."),
    NOT_MAX_LEVEL("&7You're not maximum level."),
    PRESTIGE("&7Your prestige's have been processed, &chover for details&7."),
    LEVEL_UP("&7Your level's have been processed, &chover for details&7.");

    private String message;

}
