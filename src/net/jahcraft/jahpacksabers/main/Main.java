package net.jahcraft.jahpacksabers.main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import net.jahcraft.jahpacksabers.commands.BladeColor;
import net.jahcraft.jahpacksabers.commands.GiveSaber;
import net.jahcraft.jahpacksabers.commands.Sabers;
import net.jahcraft.jahpacksabers.listeners.BladeColorListener;
import net.jahcraft.jahpacksabers.listeners.LightsaberListener;
import net.jahcraft.jahpacksabers.listeners.SaberListListener;

public class Main extends JavaPlugin {
	public static Main plugin;

	@Override
	public void onEnable() {
		
		plugin = this;
		
		Sabers.createList();
		
		getCommand("bladecolor").setExecutor((CommandExecutor) new BladeColor());
		getCommand("sabers").setExecutor((CommandExecutor) new Sabers());
		getCommand("givesaber").setExecutor((CommandExecutor) new GiveSaber());
		getServer().getPluginManager().registerEvents(new LightsaberListener(this), this);
		getServer().getPluginManager().registerEvents(new SaberListListener(), this);
		getServer().getPluginManager().registerEvents(new BladeColorListener(), this);
		
		Bukkit.getLogger().info("JahPack - Sabers || Loaded and Enabled!");


	}
	
	public void onDisable() {
		Bukkit.getLogger().info("JahPack - Sabers || Unloaded and Disabled!");
	}

}
