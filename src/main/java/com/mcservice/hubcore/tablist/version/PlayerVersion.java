package com.mcservice.hubcore.tablist.version;

import org.bukkit.entity.Player;

import com.mcservice.hubcore.tablist.reflection.ReflectionConstants;

public enum PlayerVersion {
   v1_7("v1_7", 0),
   v1_8("v1_8", 1);

   private PlayerVersion(String s, int n) {
	   
   }

   public static PlayerVersion getVersion(Player player) {
      Object handle = ReflectionConstants.GET_HANDLE_METHOD.invoke(player);
      Object connection = ReflectionConstants.PLAYER_CONNECTION.get(handle);
      Object manager = ReflectionConstants.NETWORK_MANAGER.get(connection);
      Object version = ReflectionConstants.VERSION_METHOD.invoke(manager);
      if (version instanceof Integer) {
         return ((Integer)version).intValue() > 5 ? v1_8 : v1_7;
      } else {
         return v1_7;
      }
   }
}
