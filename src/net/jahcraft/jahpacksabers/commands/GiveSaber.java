package net.jahcraft.jahpacksabers.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jahcraft.jahpacksabers.main.Lightsaber;
import net.jahcraft.jahpacksabers.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class GiveSaber implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!label.equalsIgnoreCase("givesaber")) return false;
		
		if (!sender.hasPermission("jahpack.sabers.givesaber")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}
		
		if (args.length < 3 ) {
			sender.sendMessage(ChatColor.RED + "Usage: /givesaber <player> <hiltID> <colorID> <name>");
			return true;
		}
		
		if (Bukkit.getPlayer(args[0]) == null) {
			sender.sendMessage(ChatColor.RED + "Player not found!");
			sender.sendMessage(ChatColor.RED + "Usage: /givesaber <player> <hiltID> <colorID> <name>");
			return true;
		}
		
		try {
			Integer.parseInt(args[1]);
			Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "Hilt / Color IDs must be integers!");
			sender.sendMessage(ChatColor.RED + "Usage: /givesaber <player> <hiltID> <colorID> <name>");
			return true;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		String name = "";
		for (int i = 3; i < args.length; i++) {
			name += args[i];
			if (i+1 < args.length) name += " ";
		}
		
		Lightsaber gift = new Lightsaber(Integer.parseInt(args[1]), Integer.parseInt(args[2]), false, name, true);
		
		if (recipient.getInventory().firstEmpty() == -1 ) {
		
			// full inventory	
			Location loc = recipient.getLocation();
			World world = recipient.getWorld();
			
			world.dropItemNaturally(loc, gift);
			recipient.sendMessage(Colors.GOLD + "Inventory full!" + Colors.BLUE + " Your new lightsaber was dropped at your feet!");
			
			return true;
			
		}
		
		recipient.getInventory().addItem(gift);
		recipient.sendMessage(Colors.BLUE + "Enjoy your new lightsaber!");
		
		return true;
	}

}
