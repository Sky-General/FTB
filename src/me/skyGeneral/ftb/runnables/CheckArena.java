package me.skyGeneral.ftb.runnables;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;
import me.skyGeneral.ftb.API.Colors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Sign;

public class CheckArena implements Runnable {
	World arena;
	Sign sign;
	Main plugin;
	public CheckArena(Main plugin, World arena, Sign sign) {
		this.plugin = plugin;
		this.arena = arena;
		this.sign = sign;
	}

	@Override
	public void run() {
		if(ArenaAPI.getSize(arena) > 34){
			Bukkit.getScheduler().runTaskLater(plugin, new CheckArena(plugin, arena, sign), 600);
		} else {
			sign.setLine(0, Colors.colorize("&a[JOIN]"));
			sign.setLine(2, Colors.colorize("&9" + ArenaAPI.getSize(arena) + "&7/&935"));
			sign.update();
		}
	}

}
