package net.jahcraft.jahpacksabers.util;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahpacksabers.main.Lightsaber;

public class SaberUtil {

	public static HashMap<String, ItemStack> itemGetter = getItems();
		
	private static HashMap<String, ItemStack> getItems() {

		HashMap<String, ItemStack> items = new HashMap<>();
		
		items.put("vaderlightsaber", new Lightsaber(1, 1, false, "Darth Vader's Lightsaber", true));
		items.put("nicklightsaber", new Lightsaber(2, 8, false, "Plo Koon's Lightsaber", true));
		items.put("darksaber", new Lightsaber(3, 6, false, "The Darksaber", true));
		items.put("anakinsaber", new Lightsaber(4, 3, false, "Anakin Skywalker's Lightsaber", true));
		items.put("lukesaber", new Lightsaber(5, 4, false, "Luke Skywalker's Lightsaber", true));
		items.put("macesaber", new Lightsaber(6, 5, false, "Mace Windu's Lightsaber", true));
		
		return items;
	}
	
	public static boolean isOn(ItemStack mainHand) {
		return ((mainHand.getItemMeta().getCustomModelData() % 10000) >= 2000);
	}
	
	public static boolean isFullyOn(ItemStack mainHand) {
		return ((mainHand.getItemMeta().getCustomModelData() % 10000) >= 7000);
	}
	
	public static boolean isLightsaber(ItemStack itemInMainHand) {
		if (itemInMainHand == null) return false;
		if (itemInMainHand.getType() != Material.NETHERITE_SWORD) return false;
		if (!itemInMainHand.hasItemMeta()) return false;
		if (!itemInMainHand.getItemMeta().hasCustomModelData()) return false;
		if (itemInMainHand.getItemMeta().getCustomModelData() <= 10000) return false;
		return true;
	}
	
}