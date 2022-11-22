package com.mcservice.hubcore.providers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.tablist.type.TablistInterfaze;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class TablistProvider implements TablistInterfaze {
   	
   @Deprecated 
   public Table<Integer, Integer, String> getEntries(Player player) {
	  Table<Integer, Integer, String> entries = HashBasedTable.create();
	  entries.put(Integer.valueOf(1), Integer.valueOf(0), Messages.color("&d&l&lMCTeams"));
	  entries.put(Integer.valueOf(1), Integer.valueOf(1), Messages.color("&dOnline&7: &7" + HubPlugin.getPlugin().getPlayerCount("ALL") + "/1,000"));
	  
	  entries.put(Integer.valueOf(0), Integer.valueOf(3), Messages.color("&d&l&lTeamspeak"));
	  entries.put(Integer.valueOf(0), Integer.valueOf(4), Messages.color("&7ts3.mcteams.us"));
	  
	  entries.put(Integer.valueOf(1), Integer.valueOf(3), Messages.color("&d&l&lInformation"));
	  entries.put(Integer.valueOf(1), Integer.valueOf(4), Messages.color("&7Rank: " + HubPlugin.getPlugin().getManager().getVault().getPermission().getPrimaryGroup(player)));
	  
	  if(HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
		  entries.put(Integer.valueOf(1), Integer.valueOf(6), Messages.color("&d&l&lQueueing"));
		  entries.put(Integer.valueOf(1), Integer.valueOf(7), Messages.color("&a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueueName((OfflinePlayer)player)));
		  entries.put(Integer.valueOf(1), Integer.valueOf(8), Messages.color("&a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player).getPosition(player) + " &7out &a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player).getSize()));
	  } else {
		  entries.put(Integer.valueOf(1), Integer.valueOf(6),  Messages.color(" "));
		  entries.put(Integer.valueOf(1), Integer.valueOf(7),  Messages.color(" "));
		  entries.put(Integer.valueOf(1), Integer.valueOf(8), Messages.color(" "));
	  }
	  
	  entries.put(Integer.valueOf(2), Integer.valueOf(3), Messages.color("&d&l&lStore"));
	  entries.put(Integer.valueOf(2), Integer.valueOf(4), Messages.color("&7store.mcteams.us"));
	  
	  entries.put(Integer.valueOf(0), Integer.valueOf(10), Messages.color("&d&l&lHCF"));
	  entries.put(Integer.valueOf(0), Integer.valueOf(11), Messages.color("&7Status: &r" + (HubPlugin.getPlugin().isOnline(25567) ? "&aOnline" : "&cOffline")));
	  entries.put(Integer.valueOf(0), Integer.valueOf(12), Messages.color("&7Players: &7(" + HubPlugin.getPlugin().getPlayerCount("HCF") + "/200)"));
	  
	  entries.put(Integer.valueOf(2), Integer.valueOf(10), Messages.color("&d&l&lKitMap"));
	  entries.put(Integer.valueOf(2), Integer.valueOf(11), Messages.color("&7Status: &r" + (HubPlugin.getPlugin().isOnline(25568) ? "&aOnline" : "&cOffline")));
	  entries.put(Integer.valueOf(2), Integer.valueOf(12), Messages.color("&7Players: &7(" + HubPlugin.getPlugin().getPlayerCount("KitMap") + "/200)"));
	  
	  entries.put(Integer.valueOf(1), Integer.valueOf(10), Messages.color("&d&l&lComboFly"));
	  entries.put(Integer.valueOf(1), Integer.valueOf(11), Messages.color("&7Status: &r" + (HubPlugin.getPlugin().isOnline(25569) ? "&aOnline" : "&cOffline")));
	  entries.put(Integer.valueOf(1), Integer.valueOf(12), Messages.color("&7Players: &7(" + HubPlugin.getPlugin().getPlayerCount("ComboFly") + "/200)"));
      return entries; 
   }

   public String getHeader(Player player) {
      return "";
   }

   public String getFooter(Player player) {
      return "";
   }
}
