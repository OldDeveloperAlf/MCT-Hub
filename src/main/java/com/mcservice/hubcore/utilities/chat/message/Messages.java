package com.mcservice.hubcore.utilities.chat.message;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {
	
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    public static List<String> color(List<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, color(list.get(i)));
        }
        return list;
    }
    
    public static String[] color(String[] array) {
        return color(Arrays.asList(array)).stream().toArray(String[]::new);
    }
    
    public static void sendMessage(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(color(string));
        }
    }
    
    public static void sendMessage(CommandSender commandsender, String... strings) {
        for (String string : strings) {
            commandsender.sendMessage(color(string));
        }
    }
    
    public static void broadcastMessage(String... strings) {
        for (String string : strings) {
            Bukkit.broadcastMessage(color(string));
        }
    }
    
    public static String build(String[] parts, int start) {
        String msg = "";
        for (int i = start; i < parts.length; ++i) {
            msg = msg + parts[i] + " ";
        }
        return msg.trim();
    }
    
    public static String formatLocation(Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + ", " + x + ", " + y + ", " + z;
    }
}
