package net.jahcraft.jahpacksabers.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahpacksabers.commands.BladeColor;
import net.jahcraft.jahpacksabers.util.Colors;
import net.jahcraft.jahpacksabers.util.SaberUtil;

public class BladeColorListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if (!BladeColor.menus.values().contains(e.getInventory())) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		
		e.setCancelled(true);
		
		if (!BladeColor.menus.values().contains(e.getClickedInventory())) return;
		
		Player p = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		
		if (!e.getInventory().contains(clicked)) return;
		if (!BladeColor.sabers.containsKey(p)) return;
		if (!BladeColor.menus.containsKey(p)) return;
		
		if (!SaberUtil.isLightsaber(e.getCurrentItem())) return;
		
		int newColor = e.getCurrentItem().getItemMeta().getCustomModelData()%1000;
		int oldSaberData = BladeColor.sabers.get(p).getItemMeta().getCustomModelData();
		oldSaberData = (oldSaberData/1000)*1000;
		int newSaberData = oldSaberData + newColor;
		
		ItemStack saber = BladeColor.sabers.get(p);
		ItemMeta meta = saber.getItemMeta();
		meta.setCustomModelData(newSaberData);
		saber.setItemMeta(meta);
		
		p.closeInventory();
		cleanup(p);
		p.sendMessage(Colors.BRIGHTBLUE + "Your saber color has been changed!");
		
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (BladeColor.menus.containsKey(e.getPlayer()) ||
			BladeColor.sabers.containsKey(e.getPlayer())) {
			cleanup((Player) e.getPlayer());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (BladeColor.menus.containsKey(e.getPlayer()) ||
			BladeColor.sabers.containsKey(e.getPlayer())) {
			cleanup((Player) e.getPlayer());
		}
	}
	
	private void cleanup(Player p) {
		BladeColor.menus.remove(p);
		BladeColor.sabers.remove(p);
	}
	
}