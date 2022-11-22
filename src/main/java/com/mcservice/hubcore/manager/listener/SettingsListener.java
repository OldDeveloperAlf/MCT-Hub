package com.mcservice.hubcore.manager.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class SettingsListener implements Listener {
	
	private HubPlugin plugin;
	
	public SettingsListener(HubPlugin plugin) {
		this.plugin = HubPlugin.getPlugin();
		this.plugin = plugin;
	}
	
	@EventHandler void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack itemstack = player.getItemInHand();
			if(itemstack != null) {
				if(itemstack.getType() == Material.ENDER_CHEST) {
					player.closeInventory();
					this.plugin.getManager().getSettingsManager().openSettings(player);
					event.setCancelled(true);
				}
				return;
			}
		}
	}
	
	@EventHandler void onInteract(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getInventory().getName().equals(Messages.color("&d&lPersonal Settings"))) {
			if(event.getCurrentItem() == null || (event.getSlotType() == null) || (event.getCurrentItem().getType() == Material.AIR)) {
				event.setCancelled(true);
				return;
			}
			if(event.getCurrentItem().hasItemMeta()) {
				if(event.getSlot() == 1 -1) {
					if(this.plugin.getManager().getBasic().getToggleMessage().contains(player.getUniqueId())) {
						this.plugin.getManager().getBasic().getToggleMessage().remove(player.getUniqueId());
						Messages.sendMessage(player, "&eYou will now &aable &eto receive private messages from anyone.");
						return;
					}
					this.plugin.getManager().getBasic().getToggleMessage().add(player.getUniqueId());
					Messages.sendMessage(player, "&eYou will now &cunable &eto receive private messages from anyone.");
					return;
				}
				if(event.getSlot() == 2 -1) {
					if(this.plugin.getManager().getBasic().getSounds().contains(player.getUniqueId())) {
						this.plugin.getManager().getBasic().getSounds().remove(player.getUniqueId());
						Messages.sendMessage(player, "&eYou will now &aable &eto receive private messages sounds from anyone.");
						return;
					}
					this.plugin.getManager().getBasic().getSounds().add(player.getUniqueId());
					Messages.sendMessage(player, "&eYou will now &cunable &eto receive private messages sounds from anyone.");
					return;
				}
				if(event.getSlot() == 9 -1) {
					Messages.sendMessage(player, "&cThis feature is current development :(");
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
