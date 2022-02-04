package net.prison.foggies.core.player.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SettingType {

    CRATE_MESSAGES("Crate Reward Setting",
                Arrays.asList(
                        "&7Toggle receiving crate messages",
                        "&7when you open a crate."
                )
            ),
    TOKEN_MINER("Token Miner Setting",
            Arrays.asList(
                    "&7Toggle receiving messages when your",
                    "&7Token Miner enchantment activates."
            )
    ),
    ENCHANT_MESSAGE("Enchant Upgrade Setting",
            Arrays.asList(
                    "&7Toggle receiving messages when you",
                    "&7upgrade your enchantments."
            )
    );

    private final String displayName;
    private final List<String> description;


}
