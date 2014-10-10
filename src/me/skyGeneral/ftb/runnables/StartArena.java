package me.skyGeneral.ftb.runnables;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;
import me.skyGeneral.ftb.API.Colors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class StartArena implements Runnable {
	World arena;
	Main plugin;
	public StartArena(Main plugin, World arena){
		this.plugin = plugin;
		this.arena = arena;
	}

	@Override
	public void run() {
		for(Player player : arena.getPlayers()) player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 The arena is starting in 1 minute!"));
		Bukkit.getScheduler().runTaskLater(plugin, new SecondsTillStart(plugin, arena, 5), 1100);
		ArenaAPI.setHasStarted(arena, true);
	}

}
