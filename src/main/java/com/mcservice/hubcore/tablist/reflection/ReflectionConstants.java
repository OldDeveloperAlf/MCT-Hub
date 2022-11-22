package com.mcservice.hubcore.tablist.reflection;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_7_R4.EnumProtocol;

public class ReflectionConstants {
   public static final Class<?> TAB_PACKET_CLASS = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo");
   public static final Reflection.ConstructorInvoker TAB_PACKET_CONSTRUCTOR;
   public static final Reflection.FieldAccessor TAB_PACKET_ACTION;
   public static final Reflection.FieldAccessor TAB_PACKET_NAME;
   public static final Class<?> GAME_PROFILE_CLASS;
   public static final Reflection.ConstructorInvoker GAME_PROFILE_CONSTRUCTOR;
   public static final Reflection.FieldAccessor GAME_PROFILE_NAME;
   public static final Reflection.FieldAccessor TAB_PACKET_PROFILE;
   public static final Class<?> CRAFT_PLAYER_CLASS;
   public static final Class<?> NMS_PACKET_CLASS;
   public static final Class<?> NMS_PLAYER_CLASS;
   public static final Class<?> PLAYER_CONNECTION_CLASS;
   public static final Class<?> NETWORK_MANAGER_CLASS;
   public static final Reflection.MethodInvoker GET_HANDLE_METHOD;
   public static final Reflection.MethodInvoker GET_PROFILE_METHOD;
   public static final Reflection.MethodInvoker VERSION_METHOD;
   public static final Reflection.MethodInvoker SEND_PACKET;
   public static final Reflection.FieldAccessor PLAYER_CONNECTION;
   public static final Reflection.FieldAccessor NETWORK_MANAGER;
   public static final Class<?> SCOREBOARD_TEAM_CLASS;
   public static final Reflection.ConstructorInvoker SCOREBOARD_TEAM_CONSTRUCTOR;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_NAME;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_DISPLAY_NAME;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_PREFIX;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_SUFFIX;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_PLAYERS;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_ACTION;
   public static final Reflection.FieldAccessor SCOREBOARD_TEAM_OPTIONS;
   public static final Class<?> ENUM_PROTOCOL_CLASS;
   public static final Reflection.FieldAccessor ENUM_PROTOCOL_PLAY;
   public static final Reflection.FieldAccessor ENUM_PROTOCOL_REGISTRY;

   static {
      TAB_PACKET_CONSTRUCTOR = Reflection.getConstructor(TAB_PACKET_CLASS);
      TAB_PACKET_ACTION = Reflection.getField((Class<?>)TAB_PACKET_CLASS, Integer.TYPE, 5);
      TAB_PACKET_NAME = Reflection.getField((Class<?>)TAB_PACKET_CLASS, String.class, 0);
      GAME_PROFILE_CLASS = getUntypedClasses("net.minecraft.util.com.mojang.authlib.GameProfile", "com.mojang.authlib.GameProfile");
      GAME_PROFILE_CONSTRUCTOR = Reflection.getConstructor(GAME_PROFILE_CLASS, UUID.class, String.class);
      GAME_PROFILE_NAME = Reflection.getField((Class<?>)GAME_PROFILE_CLASS, String.class, 0);
      TAB_PACKET_PROFILE = Reflection.getField((Class<?>)TAB_PACKET_CLASS, GAME_PROFILE_CLASS, 0);
      CRAFT_PLAYER_CLASS = Reflection.getCraftBukkitClass("entity.CraftPlayer");
      NMS_PACKET_CLASS = Reflection.getMinecraftClass("Packet");
      NMS_PLAYER_CLASS = Reflection.getMinecraftClass("EntityPlayer");
      PLAYER_CONNECTION_CLASS = Reflection.getMinecraftClass("PlayerConnection");
      NETWORK_MANAGER_CLASS = Reflection.getMinecraftClass("NetworkManager");
      GET_HANDLE_METHOD = Reflection.getMethod(CRAFT_PLAYER_CLASS, "getHandle");
      GET_PROFILE_METHOD = Reflection.getMethod(CRAFT_PLAYER_CLASS, "getProfile");
      VERSION_METHOD = Reflection.getMethod(NETWORK_MANAGER_CLASS, "getVersion");
      SEND_PACKET = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "sendPacket", NMS_PACKET_CLASS);
      PLAYER_CONNECTION = Reflection.getField((Class<?>)NMS_PLAYER_CLASS, PLAYER_CONNECTION_CLASS, 0);
      NETWORK_MANAGER = Reflection.getField((Class<?>)PLAYER_CONNECTION_CLASS, NETWORK_MANAGER_CLASS, 0);
      SCOREBOARD_TEAM_CLASS = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");
      SCOREBOARD_TEAM_CONSTRUCTOR = Reflection.getConstructor(SCOREBOARD_TEAM_CLASS);
      SCOREBOARD_TEAM_NAME = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 0);
      SCOREBOARD_TEAM_DISPLAY_NAME = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 1);
      SCOREBOARD_TEAM_PREFIX = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 2);
      SCOREBOARD_TEAM_SUFFIX = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 3);
      SCOREBOARD_TEAM_PLAYERS = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Collection.class, 0);
      SCOREBOARD_TEAM_ACTION = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Integer.TYPE, 0);
      SCOREBOARD_TEAM_OPTIONS = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Integer.TYPE, 1);
      ENUM_PROTOCOL_CLASS = Reflection.getMinecraftClass("EnumProtocol");
      ENUM_PROTOCOL_PLAY = Reflection.getField((Class<?>)ENUM_PROTOCOL_CLASS, ENUM_PROTOCOL_CLASS, 1);
      ENUM_PROTOCOL_REGISTRY = Reflection.getField((Class<?>)ENUM_PROTOCOL_CLASS, Map.class, 0);
   }

   public static Class<?> getUntypedClasses(String... lookupNames) {
      EnumProtocol.class.getName();
      int length = lookupNames.length;
      int i = 0;

      while(i < length) {
         String lookupName = lookupNames[i];

         try {
            return Reflection.getUntypedClass(lookupName);
         } catch (IllegalArgumentException var5) {
            ++i;
         }
      }

      throw new IllegalArgumentException("No class found in selection given");
   }
}
