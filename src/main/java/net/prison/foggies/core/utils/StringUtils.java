package net.prison.foggies.core.utils;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class StringUtils {

    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String colorPrefix(String message){
        return ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.getMessage() + message);
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, net.md_5.bungee.api.ChatColor completedColor,
                                        net.md_5.bungee.api.ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static String formatName(String input){
        String output = "";
        StringBuilder stringBuilder = new StringBuilder();

        if(input.contains("_")){
            for(String parts : input.split("_")){
                stringBuilder.append(parts.substring(0,1).toUpperCase()).append(parts.substring(1).toLowerCase()).append(" ");
            }
            output = stringBuilder.toString();
        } else if(input.split(" ").length > 1){
            for(String parts : input.split(" ")){
                stringBuilder.append(parts.substring(0,1).toUpperCase()).append(parts.substring(1).toLowerCase()).append(" ");
            }
            output = stringBuilder.toString();
        } else{
            output = input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
        }

        return output;
    }

}
