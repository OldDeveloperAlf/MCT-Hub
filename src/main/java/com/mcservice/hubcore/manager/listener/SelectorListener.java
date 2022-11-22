package com.mcservice.hubcore.manager.listener;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.maker.Item;

public class SelectorListener implements Listener {
	
	private HubPlugin plugin;
	
	public SelectorListener(HubPlugin plugin) {
		this.plugin = HubPlugin.getPlugin();
		this.plugin = plugin;
	}
	
	@EventHandler void onConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		player.setWalkSpeed(0.4f);
		player.getInventory().setItem(5 -1, this.plugin.getManager().getSelectorManager().getSelector());
        ItemStack enderpearl = new Item(Material.ENDER_PEARL).setTitle("&dEnder Butt &7(Right click to mount)").setLore("&7Use this magical item from the end", "&7to move around the hub in style.").build();
		player.getInventory().setItem(1 -1, enderpearl);
		player.getInventory().setItem(9 -1, this.plugin.getManager().getSettingsManager().getSettings());
	}
	
	@EventHandler void onDisconnect(PlayerQuitEvent event) {
		event.setQuitMessage(null);
	}
	
	@EventHandler void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack itemstack = player.getItemInHand();
			if(itemstack != null) {
				if(itemstack.getType() == Material.WATCH) {
					player.closeInventory();
					this.plugin.getManager().getSelectorManager().openServerSelector(player);
					event.setCancelled(true);
				}
				return;
			}
		}
	}
	
	@EventHandler void onInteract(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getInventory().getName().equals(Messages.color("&d&lList of Servers"))) {
			if(event.getCurrentItem() == null || (event.getSlotType() == null) || (event.getCurrentItem().getType() == Material.AIR)) {
				event.setCancelled(true);
				return;
			}
			if(event.getCurrentItem().hasItemMeta()) {
				if(event.getSlot() == 11 -1) {
					if(this.plugin.getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
						Messages.sendMessage(player, "&cYou are already queueing for " + this.plugin.getManager().getQueueManager().getQueueName((OfflinePlayer)player));
						player.closeInventory();
						return;
					}
					Messages.sendMessage(player, "&aYou have joined the queue for hcf");
					this.plugin.getManager().getQueueManager().getQueue("hcf").addEntry((OfflinePlayer)player);
					player.closeInventory();
					return;
				}
				if(event.getSlot() == 14 -1) {
					if(this.plugin.getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
						Messages.sendMessage(player, "&cYou are already queueing for " + this.plugin.getManager().getQueueManager().getQueueName((OfflinePlayer)player));
						player.closeInventory();
						return;
					}
					Messages.sendMessage(player, "&aYou have joined the queue for kitmap");
					this.plugin.getManager().getQueueManager().getQueue("kitmap").addEntry((OfflinePlayer)player);
					player.closeInventory();
					return;
				}
				if(event.getSlot() == 17 -1) {
					if(this.plugin.getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
						Messages.sendMessage(player, "&cYou are already queueing for " + this.plugin.getManager().getQueueManager().getQueueName((OfflinePlayer)player));
						player.closeInventory();
						return;
					}
					Messages.sendMessage(player, "&aYou have joined the queue for combofly-us");
					this.plugin.getManager().getQueueManager().getQueue("combofly").addEntry((OfflinePlayer)player);
					player.closeInventory();
					return;
				}
				return;
			}
			event.setCancelled(true);
			return;
		}
		
	}
}
