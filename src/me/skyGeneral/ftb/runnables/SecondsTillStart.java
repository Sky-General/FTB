package me.skyGeneral.ftb.runnables;

import java.util.Random;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.API.ArenaAPI;
import me.skyGeneral.ftb.API.Colors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SecondsTillStart implements Runnable {
	World arena;
	int i;
	Main plugin;
	public SecondsTillStart(Main plugin, World arena, int i) {
		this.plugin = plugin;
		this.arena = arena;
		this.i = i;
	}

	@Override
	public void run() {
		if(i == 1){
			for(Player player : arena.getPlayers()) player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 Arena starting in &b" + i + "&9 second..."));
		} if(i>1) {
			for(Player player : arena.getPlayers()) player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 Arena starting in &b" + i + "&9 seconds..."));
		}
		if(i==0){
			int random = new Random().nextInt(arena.getPlayers().size());
			Player chosen = arena.getPlayers().get(random);
			ArenaAPI.setChosen(arena, chosen.getName());
			for(Player player : arena.getPlayers()){
				player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 The arena has started!"));
				player.teleport(arena.getSpawnLocation());
				if(!player.equals(chosen)) player.sendMessage(Colors.colorize("&7[&9FTB&7]&9 Hurry and find the bomber! You only have 1 minute!"));
			}
			
			chosen.sendMessage(Colors.colorize("&7[&9FTB&7]&9 You have the bomb! Hurry and hide!"));
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
			for(Player player : arena.getPlayers()) player.setScoreboard(board);
			Bukkit.getScheduler().runTask(plugin, new BeginMatch(plugin, arena, 60, board.registerNewObjective(arena.getName(), "dummy")));
			return;
				
		}
		i=i-1;
		Bukkit.getScheduler().runTaskLater(plugin, new SecondsTillStart(plugin, arena, i), 20);
	}

}
