package net.prison.foggies.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Lang {

    PREFIX("&4&lVERITY ▎ "),
    PRESTIGE("&7You've prestiged a total of &c%prestiges%&7 times for &c%price%&7!"),
    NOT_ENOUGH_TOKENS("&cYou do not have enough tokens."),
    MAX_ENCHANT_LEVEL_REACHED("&cYou've reached the maximum level for this enchantment!"),
    BACKPACK_SOLD("&7You've sold &bx%amount% &7items for $%price%!"),
    MINE_ADDED_FRIEND("&7You've added a friend to your mine, they will now have access!"),
    MINE_REMOVED_FRIEND("&7You've removed a friend from your mine, they will no longer have access!"),
    NO_MINE_ACCESS("&cYou do not have the ability to do this as you're not a friend in this mine."),
    MINE_ALREADY_IN_QUEUE("&cYour Mine is already in the reset queue!"),
    NOT_ENOUGH_MONEY("&cYou do not have enough money."),
    MINE_LEVEL_UP("&7Your mine has leveled up to &b%level%&7."),
    TOGGLED_SETTING("&7You have %status% the %setting_type%."),
    NOT_ENOUGH_FRENZY_TOKENS("&7You do not have enough &bFrenzy Tokens&7."),
    ONLY_MINE_OWNER("&7Only the mine owner can do this!"),
    TOKEN_REWARD("%luck_type% &7You've received &b%tokens%&7."),
    FRENZY_ENDED("&7Unfortunately &bLucky Frenzy&7 has finished!"),
    MINE_ADDED_TO_QUEUE("&7Your Mine has been added to the queue to reset!"),
    MINE_BLOCK_CHANGED("&7You've changed your Mine Block to &c%block%&7!"),
    MINE_SIZE_INCREASED("&7Your mine size has been increased by &b%amount%&7!"),
    REACHED_MAX_MINE_SIZE("&7You've reached the Mines maximum size!"),
    MINE_CREATED("&7Your Mine has successfully been created, use &c/mine tp &7to go there."),
    GREATER_THAN_0("&cThe amount you entered is invalid, please enter an amount greater than 0."),
    GREATER_THAN_OR_EQUAL_TO_0("&cThe amount you entered is invalid, please enter an amount greater than or equal to 0."),
    BLOCK_SYMBOL("▎ ");

    private String message;

}
