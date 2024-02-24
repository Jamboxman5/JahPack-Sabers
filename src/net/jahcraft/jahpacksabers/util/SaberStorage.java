package net.jahcraft.jahpacksabers.util;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahpacksabers.main.Lightsaber;

public class SaberStorage {

	public static HashMap<String, ItemStack> itemGetter = getItems();
		
	private static HashMap<String, ItemStack> getItems() {

		HashMap<String, ItemStack> items = new HashMap<>();
		
		items.put("lightsaber", new Lightsaber(1, 1, false));
		items.put("nicklightsaber", new Lightsaber(2, 2, false));
		items.put("darksaber", new Lightsaber(3, 6, false));
		items.put("anakinsaber", new Lightsaber(4, 3, false));
		
		return items;
	}
	
}