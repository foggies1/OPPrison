package net.prison.foggies.core.mines.luckyblock.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LuckyType {

    NORMAL("&3&lLUCKY"),
    VERY("&d&lVERY LUCKY"),
    EXTREMELY("&a&lEXTREMELY LUCKY"),
    UBER("&d&lU&b&lB&e&lE&f&lR &6&lL&5&lU&c&lC&d&lK&a&lY"),
    GALACTIC("&B&LGALACTIC LUCKY");

    private final String displayName;

}
