package net.prison.foggies.core.player.commands.level;

import me.lucko.helper.Commands;
import me.lucko.helper.text3.Text;
import me.lucko.helper.text3.TextComponent;
import me.lucko.helper.text3.event.HoverEvent;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.PlayerStorage;
import net.prison.foggies.core.utils.Number;
import net.prison.foggies.core.utils.StringUtils;
import org.bukkit.entity.Player;

public class AddExperienceCMD {

    public AddExperienceCMD(OPPrison plugin) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Commands.create()
                .assertUsage("<player> <amount>")
                .handler(c -> {
                    final Player target = c.arg(0).parseOrFail(Player.class);
                    double amount = c.arg(1).parseOrFail(Double.class);

                    if(amount <= 0){
                        Players.msg(c.sender(), Lang.GREATER_THAN_0.getMessage());
                        return;
                    }

                    playerStorage.get(target.getUniqueId())
                            .whenComplete(((prisonPlayer, throwable) -> {

                                if(throwable != null){
                                    throwable.printStackTrace();
                                    return;
                                }

                                prisonPlayer.ifPresent(pp -> pp.addExperience(amount));
                                Text.sendMessage(target,
                                        TextComponent.of(StringUtils.colorPrefix("&7Your &cExperience Data &7has been updated by an &cAdmin&7, hover for details."))
                                                .hoverEvent(HoverEvent.showText(
                                                        TextComponent.of(
                                                                StringUtils.color("&7Below is information on how your &cExperience Data" + "\n" +
                                                                        "&7has been altered: " + "\n" +
                                                                        "" + "\n" +
                                                                        "&c&l" + Lang.BLOCK_SYMBOL.getMessage() + "&fExperience Added: " + Number.pretty(amount)
                                                        ))
                                                ))
                                );
                            }));

                })
                .register("expadd");

    }
}
