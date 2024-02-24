package net.jahcraft.jahpacksabers.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahpacksabers.util.SaberStorage;
import net.md_5.bungee.api.ChatColor;

public class Sabers implements CommandExecutor {
	
	public static Inventory menu;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (!(label.equalsIgnoreCase("sabers"))) return true; 
		if (!(sender instanceof Player)) return true;
			
		Player p = (Player) sender;
		
		if (!p.hasPermission("jahpack.sabers.admin")) {
			p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
			return true;
		}
		
		p.openInventory(menu);
		
//		if (!SaberStorage.itemGetter.containsKey(itemName)) {
//			p.sendMessage(ChatColor.RED + "Usage: /itemgetter <itemname>");
//			return true;
//		}
//		
//		if (SaberStorage.itemGetter.containsKey(itemName)) {
//			
//			ItemStack item = SaberStorage.itemGetter.get(itemName);
//			String iName = item.getItemMeta().getDisplayName().toString();
//			
//			if (p.getInventory().firstEmpty() == -1 ) {
//				
//				// full inventory
//				
//				Location loc = p.getLocation();
//				World world = p.getWorld();
//				
//				world.dropItemNaturally(loc, item);
//				p.sendMessage(ChatColor.of("#49B3FF") + "Inventory full. 1x " + iName + ChatColor.of("#49B3FF") + " dropped at your feet.");
//				
//				return true;
//				
//			}
//			
//			p.getInventory().addItem(item);
//			p.sendMessage(ChatColor.of("#49B3FF") + "You received 1x " + iName + ChatColor.of("#49B3FF") + ".");
//			
//			return true;
//			
//		}
		return false;
		
	}
	
	public static void createList() {
		
		int invSize = (SaberStorage.itemGetter.keySet().size()/9);
		if (SaberStorage.itemGetter.keySet().size()%9 > 0) invSize += 9;
		if (invSize > 54) invSize = 54;
		
		menu = Bukkit.createInventory(null, invSize, "JahPack - Sabers");
		
		for (ItemStack i : SaberStorage.itemGetter.values()) {
			
			menu.addItem(i);
			
		}
				
	}

}
