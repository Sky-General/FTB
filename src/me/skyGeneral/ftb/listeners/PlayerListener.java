package me.skyGeneral.ftb.listeners;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	Main plugin;
	public PlayerListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		ArenaAPI.resetPlayer(e.getPlayer(), e.getPlayer().getWorld().getSpawnLocation());
	}
}
