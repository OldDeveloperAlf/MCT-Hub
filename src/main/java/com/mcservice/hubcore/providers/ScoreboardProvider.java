package com.mcservice.hubcore.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.scoreboard.type.ScoreboardInterfaze;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class ScoreboardProvider implements ScoreboardInterfaze {

   public List<String> getScoreboardEntries(Player player) {
	      List<String> lines = new ArrayList<String>();
	      lines.add(Messages.color("&7&m-----------------------------"));
	      lines.add(Messages.color("&dOnline&7:"));
	      lines.add(Messages.color("&7" + HubPlugin.getPlugin().getPlayerCount("ALL") + "/1,000"));
	      lines.add(Messages.color(""));
	      lines.add(Messages.color("&dRank&7:"));
	      lines.add(Messages.color("&7" + HubPlugin.getPlugin().getManager().getVault().getPermission().getPrimaryGroup(player)));
	      if(HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
	    	  lines.add(Messages.color(" "));
	    	  lines.add(Messages.color("&dQueueing for &a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueueName((OfflinePlayer)player)));
	    	  lines.add(Messages.color(" &f» &dPosition &a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player).getPosition(player) + " &6out &a" + HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player).getSize()));
	      }
	      lines.add(Messages.color(" "));
	      lines.add(Messages.color("&dServer&7:"));
	      lines.add(Messages.color("&7Hub-1"));
          lines.add(Messages.color("&7&m-----------------------------"));
		return lines;
   }
}
