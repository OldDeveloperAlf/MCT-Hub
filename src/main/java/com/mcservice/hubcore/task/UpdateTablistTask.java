package com.mcservice.hubcore.task;

import java.util.stream.Stream;

import org.bukkit.Bukkit;

import com.mcservice.hubcore.manager.type.TablistManager;
import com.mcservice.hubcore.tablist.Tablist;

public class UpdateTablistTask implements Runnable {
	
	@SuppressWarnings("deprecation")
	public void run() {
      TablistManager manager = TablistManager.INSTANCE;
      if (manager != null) {
         Stream.of(Bukkit.getOnlinePlayers()).forEach((player) -> {
            Tablist tablist = manager.getTablist(player);
            if (tablist != null) {
               tablist.hideRealPlayers().update();
            }

         });
      }
   }
}
