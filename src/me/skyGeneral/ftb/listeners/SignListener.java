package me.skyGeneral.ftb.listeners;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;
import me.skyGeneral.ftb.API.Colors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {
	Main plugin;
	public SignListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onPlayerEditSign(SignChangeEvent e){
		if(!e.getLine(0).equalsIgnoreCase("[Find the Bomb]")) return;
		e.setLine(0, Colors.colorize("&a[JOIN]"));
		e.setLine(1, Colors.colorize("&7" + e.getLine(1)));
		e.setLine(2, Colors.colorize("&90&7/&935"));
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getClickedBlock() == null) return;
		if(!(e.getClickedBlock().getState() instanceof Sign)) return;
		Sign sign = (Sign) e.getClickedBlock().getState();
		World arena;
		Player player = e.getPlayer();
		try{
			arena = Bukkit.getWorld(ChatColor.stripColor(sign.getLine(1)));
			arena.setStorm(false);
		} catch(Exception ex){
			player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 Sorry, there was an unknown error when trying to take you to this arena."));
			return;
		}
		ArenaAPI.addPlayerToArena(plugin, player, arena, "FTB", sign);
	}

}

