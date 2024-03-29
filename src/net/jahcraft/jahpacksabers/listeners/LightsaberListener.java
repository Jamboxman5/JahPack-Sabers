package net.jahcraft.jahpacksabers.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahpacksabers.main.Main;
import net.jahcraft.jahpacksabers.util.SaberUtil;

public class LightsaberListener implements Listener {
	
	public static HashMap<ItemStack, Long> cooldowns = new HashMap<>();
	public static HashMap<Player, Long> swingCooldowns = new HashMap<>();
	public static List<ItemStack> toggling = new ArrayList<>();
	
	private Main plugin;
	
	public LightsaberListener(Main m) {
		plugin = m;
	}
	
	@EventHandler
	public void onEnchant(PrepareItemEnchantEvent e) {
		if (SaberUtil.isLightsaber(e.getItem())) e.setCancelled(true);
	}
	
	@EventHandler
	public void onAnvil(PrepareAnvilEvent e) {
		for (ItemStack i : e.getInventory().getContents()) {
			if (SaberUtil.isLightsaber(i)) {
				e.setResult(null);
//				e.getInventory().setRepairCost(0);
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!SaberUtil.isLightsaber(e.getItemDrop().getItemStack())) return;
		if (!SaberUtil.isOn(e.getItemDrop().getItemStack())) return;
		
		int oldData = e.getItemDrop().getItemStack().getItemMeta().getCustomModelData();
		int state = ((oldData % 10000) / 1000) * 1000;
		
		ItemStack saber = e.getItemDrop().getItemStack();
		ItemMeta meta = saber.getItemMeta();
		meta.setCustomModelData(oldData - (state-1000));
		saber.setItemMeta(meta);
		
		e.getItemDrop().setItemStack(saber);
		
	}
	
	@EventHandler
	public void onSwing(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		if (!SaberUtil.isLightsaber(e.getPlayer().getInventory().getItemInMainHand())) return;
		if (!SaberUtil.isFullyOn(e.getPlayer().getInventory().getItemInMainHand())) return;
		if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
		Player p = e.getPlayer();
		p.getWorld().playSound(p.getLocation(), "jahpack.sabers.swing", 1, 1);	
		swingCooldowns.put(p, System.currentTimeMillis());

		
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		
		if (!(e.getDamager() instanceof Player)) return;
		
		Player damager = (Player) e.getDamager();
		ItemStack mainHand = damager.getInventory().getItemInMainHand();

		if (!SaberUtil.isLightsaber(mainHand)) return;
		damager.getWorld().playSound(e.getEntity().getLocation(), "jahpack.sabers.hit", 1, 1);	

		if (SaberUtil.isFullyOn(mainHand)) {
			e.getEntity().setFireTicks(10);
			if (e.getEntity() instanceof Player) e.setDamage(e.getDamage());
			else {
				if (swingCooldowns.containsKey(damager)) {
					if (System.currentTimeMillis() - swingCooldowns.get(damager) < 800) return;
				}
				swingCooldowns.put(damager, System.currentTimeMillis());
				e.setDamage(40);
			}
		} else {
			e.setDamage(1);
		}
		
		
		
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_AIR &&
			e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		
		ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
		
		if (!SaberUtil.isLightsaber(mainHand)) return;
		if (!isReady(mainHand)) return;
							
		if (e.getPlayer().isSneaking()) toggleLightsaber(mainHand, e.getPlayer());
				
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
							if (meta.getCustomModelData()%1000 == 1) {
								p.getWorld().playSound(p.getLocation(), "jahpack.sabers.retract.sith", 1, 1);	
							} else {
								p.getWorld().playSound(p.getLocation(), "jahpack.sabers.retract.jedi", 1, 1);	
							}
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
							if (meta.getCustomModelData()%1000 == 1) {
								p.getWorld().playSound(p.getLocation(), "jahpack.sabers.ignite.sith", 1, 1);	
							} else {
								p.getWorld().playSound(p.getLocation(), "jahpack.sabers.ignite.jedi", 1, 1);	
							}						}
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

	

}
