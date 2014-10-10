package me.skyGeneral.ftb.API;

import java.util.HashMap;
import java.util.Map;

import me.skyGeneral.ftb.Main;
import me.skyGeneral.ftb.runnables.CheckArena;
import me.skyGeneral.ftb.runnables.StartArena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ArenaAPI {
	Main plugin;
	/* String = World name */
	public static Map<String, Boolean> arenaStarted = new HashMap<String, Boolean>();
	/* String = World name */
	public static Map<String, Integer> arenaSize = new HashMap<>();
	/* String = Player name */
	public static Map<String, PlayerState> playerState = new HashMap<>();
	/* String = Player name */
	public static Map<String, World> redTeam = new HashMap<String, World>();
	/* String = Player name */
	public static Map<String, World> blueTeam = new HashMap<String, World>();
	
	public static Map<World, String> chosen = new HashMap<World, String>();

	private static String prefix = ChatColor.translateAlternateColorCodes('&',
			"&7[&9NAME&7] &9");

	private static String size = ChatColor.translateAlternateColorCodes('&',
			"&7(&9l&7/&935&7)");

	@SuppressWarnings("deprecation")
	public static Player getChosen(World arena){
		return Bukkit.getPlayer(chosen.get(arena));
	}
	public static void setChosen(World arena, String player){
		chosen.put(arena, player);
	}
	public static void setSize(World arena, int size) {
		arenaSize.put(arena.getName(), size);
	}
	
	public static void addPlayerToArena(Main plugin, Player player, World arena, String gameName, Sign sign) {
		prefix = prefix.replaceFirst("NAME", gameName);
		if (!arenaSize.containsKey(arena.getName())) arenaSize.put(arena.getName(), 0);
		int asize = arenaSize.get(arena.getName());
		size = "&7(&9" + (asize+1) + "&7/&935&7)";
		if(arenaSize.get(arena.getName())>34){
			sign.setLine(0, Colors.colorize("&c[FULL]"));
			sign.update();
			Bukkit.getScheduler().runTaskLater(plugin, new CheckArena(plugin, arena, sign), 20);
		}
		if (arenaSize.get(arena.getName()) > 34) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry. That arena is full."));
			return;
		} else {
			if(!ArenaAPI.arenaStarted.containsKey(arena)) ArenaAPI.setHasStarted(arena, false);
			if(ArenaAPI.hasStarted(arena)){
				player.sendMessage(Colors.colorize(prefix + "Oops. That game as already started."));
				return;
			}
			arenaSize.put(arena.getName(), (asize + 1));
			sign.setLine(2, Colors.colorize("&9" + ArenaAPI.getSize(arena) + "&7/&935"));
			sign.update();
			player.teleport(arena.getSpawnLocation());
			for(Player p : arena.getPlayers()) p.sendMessage(Colors.colorize(prefix + player.getName() + " has joined! " + size));
			if(ArenaAPI.getSize(arena) == 4){
				Bukkit.getScheduler().runTask(plugin, new StartArena(plugin, arena));
			}
		}
	}

	public static void resetPlayer(Player player, Location spawn) {
		player.setGameMode(GameMode.SURVIVAL);
		playerState.put(player.getName(), PlayerState.INLOBBY);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setMaxHealth(2.0);
		player.setHealth(2.0);
		player.teleport(spawn);
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		for (Player p : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
			p.showPlayer(player);
		}
	}
	
	public static void checkArena(World arena, Location spawn, boolean hasTeams, String gameName, Player winner){
		if(hasTeams){
			prefix = prefix.replaceFirst("NAME", gameName);
			int redTeamSize = 0;
			int blueTeamSize = 0;
			for (int r1 = redTeam.size(); r1 != 0; r1--)
				if (redTeam.containsValue(arena))
					redTeamSize++;
			for (int b1 = blueTeam.size(); b1 != 0; b1--)
				if (blueTeam.containsValue(arena))
					blueTeamSize++;
			if (!arenaStarted.get(arena.getName()))
				return;
			if (redTeamSize == 0) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.getWorld().equals(arena)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', prefix + " The &bblue &8team won!"));
						arenaStarted.put(arena.getName(), false);
						resetPlayer(player, spawn);
						return;
					}
				}
			}
			if (blueTeamSize == 0) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.getWorld().equals(arena)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', prefix + " The &cred &8team won!"));
						player.teleport(spawn);
						arenaStarted.put(arena.getName(), false);
						resetPlayer(player, spawn);
						return;
					}
				}
			}
		
		}
		if(!hasTeams){
			prefix = prefix.replaceFirst("NAME", gameName);
			for(Player p : arena.getPlayers()){
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + winner.getName() + " has won!"));
				resetPlayer(p, spawn);
				arenaStarted.put(arena.getName(), false);
				p.teleport(spawn);
			}
		}
	}

	public static void endArena(World arena) {
		arenaStarted.put(arena.getName(), false);
		arenaSize.put(arena.getName(), 0);

	}
	public static PlayerState getPlayerState(Player player){
		return playerState.get(player.getName());
	}

	public static int getSize(World arena) {
		if (arenaSize.containsKey(arena.getName())) {
			return arenaSize.get(arena.getName());
		} else {
			System.out.println("Arena does not exist!");
			return -1;
		}
	}

	public static Boolean hasStarted(World arena) {
		return arenaStarted.get(arena.getName());
	}

	public static void setHasStarted(World arena, boolean started) {
		arenaStarted.put(arena.getName(), started);
	}

	public static void setPlayerState(PlayerState state, Player player) {
		playerState.put(player.getName(), state);
	}
}
