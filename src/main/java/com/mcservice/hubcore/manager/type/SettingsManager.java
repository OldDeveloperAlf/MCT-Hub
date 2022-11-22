package com.mcservice.hubcore.manager.type;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.maker.Item;

public class SettingsManager {
	
	private HubPlugin plugin;
	
	public SettingsManager(HubPlugin plugin) {
		this.plugin = HubPlugin.getPlugin();
		this.plugin = plugin;
	}
	
	public void openSettings(Player player) {
		Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9 * 1, Messages.color("&d&lPersonal Settings"));

		new BukkitRunnable() {
			
			@Override
			public void run() {
				BasicManager basic = SettingsManager.this.plugin.getManager().getBasic();
				ItemStack stained = new Item(Material.STAINED_GLASS_PANE).setTitle("&0 ").setData(15).build();
				ItemStack togglemessage = new Item(basic.getToggleMessage().contains(player.getUniqueId()) ? Material.BOOK_AND_QUILL : Material.EMPTY_MAP).setTitle(basic.getToggleMessage().contains(player.getUniqueId()) ? "&aPrivate Messages" : "&cPrivate Messages").setLore("&7&m--------------------------------", "&7Toggles receiving private messages", "&7from anyone.", " ", basic.getToggleMessage().contains(player.getUniqueId()) ? " &dClick to enable private message" : " &dClick to disable private message", "&7&m--------------------------------").build();
				ItemStack togglesounds = new Item(basic.getSounds().contains(player.getUniqueId()) ? Material.NOTE_BLOCK : Material.RECORD_11).setTitle(basic.getToggleMessage().contains(player.getUniqueId()) ? "&aPrivate Messages Sounds" : "&cPrivate Messages Sounds").setLore("&7&m--------------------------------", "&7Toggles hearing a sound when you receive ", "&7a privates messages from anyone.", " ", basic.getToggleMessage().contains(player.getUniqueId()) ? " &dClick to enable private message sounds" : " &dClick to disable private message sounds", "&7&m--------------------------------").build();
				ItemStack statistics = new Item(Material.DIAMOND_SWORD).setTitle("&dPlayer Statistics").setLore("&7&m--------------------------------", "&7Check your statistics:", "&7Kills, Deaths, Balance, Tops", " ", "&dClick to open statistics menu.", "&7&m--------------------------------").build();
				for(int i = 0; i < inventory.getSize(); i++) {
					if(inventory.getItem(i) == null) {
						inventory.setItem(i, stained);
					}
				}
				inventory.setItem(1 -1, togglemessage);
				inventory.setItem(2 -1, togglesounds);
				inventory.setItem(9 -1, statistics);
				if(!player.getOpenInventory().getTitle().equals(Messages.color("&d&lPersonal Settings"))) {
					this.cancel();
				}
			}
		}.runTaskTimer((Plugin)this.plugin, 0L, 0L);
		player.openInventory(inventory);
	}
	
	public ItemStack getSettings() {
		ItemStack item = new Item(Material.ENDER_CHEST).setTitle("&dPlayer Settings &7(Rigth click to open)").setLore(" ").build();
		return item;
	}
}
