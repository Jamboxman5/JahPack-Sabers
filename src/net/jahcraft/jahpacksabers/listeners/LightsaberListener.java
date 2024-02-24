package net.jahcraft.jahpacksabers.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahpacksabers.main.Main;

public class LightsaberListener implements Listener {
	
	public static HashMap<ItemStack, Long> cooldowns = new HashMap<>();
	public static List<ItemStack> toggling = new ArrayList<>();
	
	private Main plugin;
	
	public LightsaberListener(Main m) {
		plugin = m;
	}
	
	@EventHandler
	public void onEnchant(PrepareItemEnchantEvent e) {
		if (isLightsaber(e.getItem())) e.setCancelled(true);
	}
	
	@EventHandler
	public void onAnvil(PrepareAnvilEvent e) {
		for (ItemStack i : e.getInventory().getContents()) {
			if (isLightsaber(i)) {
				e.setResult(null);
//				e.getInventory().setRepairCost(0);
			}
		}
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		
		if (!(e.getDamager() instanceof Player)) return;
		
		Player damager = (Player) e.getDamager();
		ItemStack mainHand = damager.getInventory().getItemInMainHand();

		if (!isLightsaber(mainHand)) return;
		if (isOn(mainHand)) {
			e.getEntity().setFireTicks(10);
			e.setDamage(e.getDamage());
		} else {
			e.setDamage(1);
		}
		
		
		
	}
	
	private boolean isOn(ItemStack mainHand) {
		return ((mainHand.getItemMeta().getCustomModelData() % 100) >= 70);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		
		ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
		
		if (!isLightsaber(mainHand)) return;
		if (!isReady(mainHand)) return;
							
		toggleLightsaber(mainHand, e.getPlayer());
				
	}

	private void toggleLightsaber(ItemStack lightSaber, Player p) {
		
		ItemMeta meta = lightSaber.getItemMeta();
		boolean on;
		toggling.add(lightSaber);
		
		if ((meta.getCustomModelData() % 10000) < 2000) on = false;
		else on = true;
		
		if (on) {
			
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					new BukkitRunnable() {
						@Override
						public void run() {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 50);	
						}
					}.runTask(plugin);
					while (((meta.getCustomModelData() % 10000) - 1000) > 1000) {
						meta.setCustomModelData(meta.getCustomModelData()-1000);
						lightSaber.setItemMeta(meta);

						try {
							Thread.sleep(60);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					toggling.remove(lightSaber);
				}
				
			});
			
			
		} else {
			
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					new BukkitRunnable() {
						@Override
						public void run() {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 50);
						}
					}.runTask(plugin);
					while (((meta.getCustomModelData() % 10000) + 1000) < 8000) {
						meta.setCustomModelData(meta.getCustomModelData()+1000);

						lightSaber.setItemMeta(meta);

						try {
							Thread.sleep(60);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					toggling.remove(lightSaber);
				}
				
			});
			
		}
		
		
	}

	private boolean isReady(ItemStack itemInMainHand) {
		if (toggling.contains(itemInMainHand)) return false;
		return true;
	}

	private boolean isLightsaber(ItemStack itemInMainHand) {
		if (itemInMainHand == null) return false;
		if (itemInMainHand.getType() != Material.NETHERITE_SWORD) return false;
		if (!itemInMainHand.hasItemMeta()) return false;
		if (!itemInMainHand.getItemMeta().hasCustomModelData()) return false;
		if (itemInMainHand.getItemMeta().getCustomModelData() <= 10000) return false;
		return true;
	}

}
