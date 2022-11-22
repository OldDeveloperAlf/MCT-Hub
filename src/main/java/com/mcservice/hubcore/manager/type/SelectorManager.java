package com.mcservice.hubcore.manager.type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.maker.Item;

public class SelectorManager{
	
	public void openServerSelector(Player player) {
		Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9 * 5, Messages.color("&d&lList of Servers"));
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ItemStack stained = new Item(Material.STAINED_GLASS_PANE).setTitle("&0 ").setData(15).build();
				List<String> hcflore = new ArrayList<String>();
				hcflore.add(Messages.color("&7&m----------------------------------------------"));
				hcflore.add(Messages.color(" &dServer Information&7:"));
				hcflore.add(Messages.color("  &f» &dPlaying &f" + HubPlugin.getPlugin().getPlayerCount("HCF")));
				hcflore.add(Messages.color("  &f» &dStatus &r" + (HubPlugin.getPlugin().isOnline(25567) ? "&aOnline" : "&cOffline")));
				hcflore.add(Messages.color(" "));
				hcflore.add(Messages.color(" &dDescription&7:"));
				hcflore.add(Messages.color(" &7Play against other factions to become"));
				hcflore.add(Messages.color(" &7the top faction on the server. Along with"));
				hcflore.add(Messages.color(" &7the endless PvP, event such as palace and"));
				hcflore.add(Messages.color(" &7king of the hill will take place throughout"));
				hcflore.add(Messages.color(" &7the entirety of the map."));
				hcflore.add(Messages.color(" "));
				hcflore.add(Messages.color(" &dCheatbreaker&7:"));
				hcflore.add(Messages.color(" &7You do not need &cCheat&fBreaker &7to join this mode."));
				hcflore.add(Messages.color(" "));
				hcflore.add(Messages.color(" &dRecommended game version&7: &71.7, 1.8 or CheatBreaker"));
				hcflore.add(Messages.color(" &7Click to connect this server."));
				hcflore.add(Messages.color("&7&m----------------------------------------------"));
				ItemStack hcf = new Item((HubPlugin.getPlugin().isOnline(25567) ? Material.DIAMOND_SWORD : Material.REDSTONE_BLOCK)).setTitle((HubPlugin.getPlugin().isOnline(25567) ? "&a&lHardcore Factions" : "&c&lHardcore Factions")).setLore(hcflore).build();
				
				List<String> kitmaplore = new ArrayList<String>();
				kitmaplore.add(Messages.color("&7&m----------------------------------------------"));
				kitmaplore.add(Messages.color(" &dServer Information&7:"));
				kitmaplore.add(Messages.color("  &f» &dPlaying &f" + HubPlugin.getPlugin().getPlayerCount("KitMap")));
				kitmaplore.add(Messages.color("  &f» &dStatus &r" + (HubPlugin.getPlugin().isOnline(25568) ? "&aOnline" : "&cOffline")));
				kitmaplore.add(Messages.color(" "));
				kitmaplore.add(Messages.color(" &dDescription&7:"));
				kitmaplore.add(Messages.color(" &7Simulate the never ending PvP that takes"));
				kitmaplore.add(Messages.color(" &7place on HCF. Duel other teams with our"));
				kitmaplore.add(Messages.color(" &7numerous preset gamemodes to prove which"));
				kitmaplore.add(Messages.color(" &7faction is the best."));
				kitmaplore.add(Messages.color(" "));
				kitmaplore.add(Messages.color(" &dCheatbreaker&7:"));
				kitmaplore.add(Messages.color(" &7You do not need &cCheat&fBreaker &7to join this mode."));
				kitmaplore.add(Messages.color(" "));
				kitmaplore.add(Messages.color(" &dRecommended game version&7: &71.7, 1.8 or CheatBreaker"));
				kitmaplore.add(Messages.color(" &7Click to connect this server."));
				kitmaplore.add(Messages.color("&7&m----------------------------------------------"));
				ItemStack kitmap = new Item((HubPlugin.getPlugin().isOnline(25568) ? Material.POTION : Material.REDSTONE_BLOCK)).setData(16453).setTitle((HubPlugin.getPlugin().isOnline(25568) ? "&a&lKitMap" : "&c&lKitMap")).setLore(kitmaplore).build();
				
				List<String> comboflylore = new ArrayList<String>();
				comboflylore.add(Messages.color("&7&m----------------------------------------------"));
				comboflylore.add(Messages.color(" &dServer Information&7:"));
				comboflylore.add(Messages.color("  &f» &dPlaying &f" + HubPlugin.getPlugin().getPlayerCount("ComboFly")));
				comboflylore.add(Messages.color("  &f» &dStatus &r" + (HubPlugin.getPlugin().isOnline(25569) ? "&aOnline" : "&cOffline")));
				comboflylore.add(Messages.color(" "));
				comboflylore.add(Messages.color(" &dDescription&7:"));
				comboflylore.add(Messages.color(" &7Simulates the endless PvP that takes place in Practice,"));
				comboflylore.add(Messages.color(" &7Duels against other players in a Hit Delay of 0-1."));
				comboflylore.add(Messages.color(" "));
				comboflylore.add(Messages.color(" &dCheatbreaker&7:"));
				comboflylore.add(Messages.color(" &7You do not need &cCheat&fBreaker &7to join this mode."));
				comboflylore.add(Messages.color(" "));
				comboflylore.add(Messages.color(" &dRecommended game version&7: &71.7, 1.8 or CheatBreaker"));
				comboflylore.add(Messages.color(" &7Click to connect this server."));
				comboflylore.add(Messages.color("&7&m----------------------------------------------"));
				ItemStack combofly = new Item((HubPlugin.getPlugin().isOnline(25569) ? Material.GOLDEN_APPLE : Material.REDSTONE_BLOCK)).setData(1).setTitle((HubPlugin.getPlugin().isOnline(25569) ? "&a&lComboFly" : "&c&lComboFly")).setLore(comboflylore).build();
				for(int i = 0; i < inventory.getSize(); i++) {
					if(inventory.getItem(i) == null) {
						inventory.setItem(i, stained);
						inventory.setItem(11 -1, hcf);
						inventory.setItem(14 -1, kitmap);
						inventory.setItem(17 -1, combofly);
					}
				}
				if(!player.getOpenInventory().getTitle().equals(Messages.color("&d&lList of Servers"))) {
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(HubPlugin.getPlugin(), 0L, 0L);
		player.openInventory(inventory);
	}
	
	public ItemStack getSelector() {
		ItemStack item = new Item(Material.WATCH).setTitle("&dSelector &7(Rigth click to open)").setLore("&7Use this item to connect to", "&7the server you wish to play on.").build();
		return item;
	}
}
