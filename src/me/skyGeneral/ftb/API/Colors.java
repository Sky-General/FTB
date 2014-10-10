package me.skyGeneral.ftb.API;

import org.bukkit.ChatColor;

public class Colors {
	public static String colorize(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
