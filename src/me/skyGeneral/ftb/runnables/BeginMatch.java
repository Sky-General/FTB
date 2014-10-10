package me.skyGeneral.ftb.runnables;

import java.util.Random;
import java.util.UUID;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.Colors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BeginMatch implements Runnable {
	World arena;
	Main plugin;
	int i;
	Objective obj;
	public BeginMatch(Main plugin, World arena, int i, Objective obj){
		this.plugin = plugin;
		this.arena = arena;
		this.i = i;
		this.obj = obj;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for(Player player : arena.getPlayers()){
			obj.setDisplayName(Colors.colorize("&9Find The Bomb"));
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			Score time = obj.getScore(Bukkit.getOfflinePlayer("Time"));
			time.setScore(i);
			
			}
		i=i-1;
		if(i == 0){
			Bukkit.broadcastMessage("GAME OVER");
			return;
		} else {
			Bukkit.getScheduler().runTaskLater(plugin, new BeginMatch(plugin, arena, i, obj), 20);
		}

	}

}
