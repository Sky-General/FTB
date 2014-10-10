package me.skyGeneral.ftb.commands;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;
import me.skyGeneral.ftb.API.PlayerState;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {
	Main plugin;
	public LeaveCommand(Main plugin, String cmd){
		this.plugin = plugin;
		plugin.getCommand(cmd).setExecutor(this);
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("leave")){
			if(sender instanceof Player){
				Player player = (Player) sender;
//				if(ArenaAPI.getPlayerState(player) == PlayerState.INGAME || ArenaAPI.getPlayerState(player) == PlayerState.INLOBBY || ArenaAPI.getPlayerState(player) == PlayerState.SPECTATOR){
					int asize = ArenaAPI.getSize(player.getWorld());
					ArenaAPI.arenaSize.put(player.getWorld().getName(), asize-1);
					ArenaAPI.resetPlayer(player, Bukkit.getWorld("world").getSpawnLocation());
				}
//			}
		}
		return false;
	}

}
