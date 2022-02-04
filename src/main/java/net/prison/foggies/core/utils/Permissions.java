package net.prison.foggies.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {

    PRESTIGE_ADMIN("prestige.admin"),
    MINE_EMPTY("mine.empty"),
    TOKEN_ADMIN("token.admin"),
    FRENZY_GIVE("frenzy.give"),
    PICKAXE_GIVE("pickaxe.give"),
    PLAYER_DATA("player.data"),
    LEVEL_ADMIN("level.admin");

    private String permission;

}
