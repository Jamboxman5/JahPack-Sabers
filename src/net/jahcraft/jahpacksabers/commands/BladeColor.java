package net.jahcraft.jahpacksabers.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahpacksabers.main.Lightsaber;
import net.md_5.bungee.api.ChatColor;

public class BladeColor implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("bladecolor")) return false;
		if (!(sender instanceof Player)) return true;
		if (!sender.hasPermission("jahitems.bladecolor")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}
		
		Player p = (Player) sender;
				
		ItemStack mainHand = p.getInventory().getItemInMainHand();
		
		if (!isLightsaber(mainHand)) {
			p.sendMessage(ChatColor.RED + "You must be holding a lightsaber to do that!");
			return true;
		}
		
//		if (args.length != 1) {
//			p.sendMessage(ChatColor.RED + "Usage: /bladecolor <color>");
//			return true;
//		}
//		
//		if (!colorIDs.contains(args[0].toLowerCase())) {
//			p.sendMessage(ChatColor.RED + "Color not found!");
//			return true;
//		}
		
		Inventory inv = Bukkit.createInventory(null, 9, "Choose a Color:");
		int hilt = (mainHand.getItemMeta().getCustomModelData()/10000);
		
		for (int i = 1; i <= getValues().size(); i++) {
			inv.addItem(new Lightsaber(hilt, i, true));
		}
		p.openInventory(inv);
		
		
		//setBladeColor(args[0], mainHand);

		return true;

	}
	
	private ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<>();
		
		values.add("red");
		values.add("nickyorange");
		values.add("blue");
		values.add("green");
		values.add("purple");
		values.add("black");
		values.add("gold");
		values.add("deepblue");
		
		return values;
	}

//	private void setBladeColor(String color, ItemStack mainHand) {
//		int colorID = colorIDs.indexOf(color.toString()) + 1;
//		ItemMeta meta = mainHand.getItemMeta();
//		int curModelData = meta.getCustomModelData();
////		Bukkit.broadcastMessage("" + mainHand.getItemMeta().getCustomModelData());
//
////		Bukkit.broadcastMessage(curModelData + "");
//		
//		curModelData = curModelData - (curModelData % 10);
////		Bukkit.broadcastMessage(curModelData + "");
//		curModelData += colorID;
////		Bukkit.broadcastMessage(curModelData + "");
//		meta.setCustomModelData(curModelData);
//		mainHand.setItemMeta(meta);
//		
////		Bukkit.broadcastMessage("" + mainHand.getItemMeta().getCustomModelData());
//		
//		
//		
//	}

	private boolean isLightsaber(ItemStack itemInMainHand) {
		if (itemInMainHand == null) return false;
		if (itemInMainHand.getType() != Material.NETHERITE_SWORD) return false;
		if (!itemInMainHand.hasItemMeta()) return false;
		if (!itemInMainHand.getItemMeta().hasCustomModelData()) return false;
		if (itemInMainHand.getItemMeta().getCustomModelData() <= 10000) return false;
		return true;
	}
	
}
