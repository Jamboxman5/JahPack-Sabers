package net.jahcraft.jahpacksabers.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Lightsaber extends ItemStack {
	
	public Lightsaber(int hiltID, int colorID, boolean on) {
		
		super(Material.NETHERITE_SWORD);
		
		int stateID = 1000;
		if (on) stateID = 7000;
		colorID = colorID % 1000;
		hiltID = hiltID*10000;
		
		int modelData = hiltID + stateID + colorID;
		
		ItemMeta meta = getItemMeta();
		meta.setCustomModelData(modelData);
		meta.setUnbreakable(true);
		meta.setDisplayName(ChatColor.of("#FFD700") + "Lightsaber");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.of("#49B3FF") + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "=-=-=-=-=-=-=");
		lore.add(ChatColor.of("#FFD700") + "" + ChatColor.BOLD + "Offical Jah Donor Item!");
		lore.add(ChatColor.of("#49B3FF") + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "=-=-=-=-=-=-=");
		lore.add("");
		lore.add(ChatColor.of("#779AD8") + "An elegant weapon from a");
		lore.add(ChatColor.of("#779AD8") + "more civilized age.");
		lore.add("");
		lore.add(ChatColor.of("#49B3FF") + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "=-=-=-=-=-=-=");
		lore.add("");
		meta.setLore(lore);
		
		setItemMeta(meta);
		
	}

}
