package com.mcservice.hubcore.tablist.type;

import org.bukkit.entity.Player;

import com.google.common.collect.Table;

public interface TablistInterfaze {
	
   @SuppressWarnings("rawtypes") Table getEntries(Player var1);

   String getHeader(Player var1);

   String getFooter(Player var1);
}
