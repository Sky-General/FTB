package me.skyGeneral.ftb;

import me.skyGeneral.ftb.commands.LeaveCommand;
import me.skyGeneral.ftb.listeners.PlayerListener;
import me.skyGeneral.ftb.listeners.SignListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable(){
		new SignListener(this);
		new PlayerListener(this);
		new LeaveCommand(this, "leave");
	}

}
