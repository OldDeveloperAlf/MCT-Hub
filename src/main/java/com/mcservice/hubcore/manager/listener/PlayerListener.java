package com.mcservice.hubcore.manager.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;

import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {
	
	@EventHandler void onConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location spawn = Bukkit.getWorld("world").getSpawnLocation().clone();
		spawn.add(0.5, 0.5, 0.5);
		player.teleport(spawn);
		Messages.sendMessage(player, "&7&m-----------------------------------------------");
		Messages.sendMessage(player, " &7Welcome to the &d&lMCTeams Network&f.");
		Messages.sendMessage(player, " ");
		Messages.sendMessage(player, " &f» &dStore&7: &7store.mcteams.us");
		Messages.sendMessage(player, " &f» &dTwitter&7: &7@MCTeamsNET");
		Messages.sendMessage(player, " &f» &dTeamspeak&7: &7ts3.mcteams.us");
		Messages.sendMessage(player, " ");
		Messages.sendMessage(player, "&7&m-----------------------------------------------");
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 3F, 3F);
	}
	
	@EventHandler void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Player p = (Player)e.getEntity();
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
        e.setCancelled(true);
    }
    
    @EventHandler void onFoodLevelChange(FoodLevelChangeEvent e) {	
        e.setCancelled(true);
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (p.getFoodLevel() != 20) {
                p.setFoodLevel(20);
            }
        }
    }
    
    @EventHandler void onSpawn(EntitySpawnEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler void onTarget(EntityTargetEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onTarget2(EntityTargetLivingEntityEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler void onChat(AsyncPlayerChatEvent event) {
    	Player player = event.getPlayer();
    	event.setFormat(Messages.color("&r" + HubPlugin.getPlugin().getManager().getVault().getChat().getPlayerPrefix(player) + player.getName() + "&7: &f") + ChatColor.stripColor(event.getMessage()));
    }
    
    @EventHandler void onPreCommand(PlayerCommandPreprocessEvent event) {
    	Player player = event.getPlayer();
    	String command = event.getMessage().split(" ")[0];
    	List<String> blocked = Arrays.asList("relaod", "me", "say", "bukkit", "bukkit:", "bukkit:me", "bukkit:tell", "bukkit:msg", "bukkit:w", "bukkit", "bukkit:say", "minecraft:me", "minecraft:say", "minecraft", "/calc", "/set", "/replace", "replace", "op", "deop", "rl", "pex", "permissionsex:pex", "demote", "promote", "permissionsex:promote", "permissionsex:demote", "worldedit", "we");
    	List<String> plugins  = Arrays.asList("plugins", "pl", "minecraft:plugins", "minecraft:pl", "bukkit:pl", "bukkit:plugins", "?", "help", "bukkit:help", "bukkit:?", "minecraft:?", "minecraft:help");
    	List<String> versions = Arrays.asList("version", "ver", "bukkit:ver", "bukkit:version", "icanhasbukkit", "about", "bukkit:about", "minecraft:about");
    	List<String> hub = Arrays.asList("hubcore", "hub", "core", "basic");
    	if (command.startsWith("/")) {
            command = command.substring(1, command.length());
        }
    	if(blocked.contains(command.toLowerCase())) {
    		Messages.sendMessage(player, "Unknown command. Type \"/help\" for help.");
    		event.setCancelled(true);
    		return;
    	}
    	if(plugins.contains(command.toLowerCase())) {
    		Messages.sendMessage(player, "&dPlugins (3): &fVault&7(&a1.5.6-B49&7)&f, &fMCT-HUB&7(&a" + HubPlugin.getPlugin().getDescription().getVersion() + "&7)&f, &fSql-Java-Client&7(&a2.7&7)");
    		event.setCancelled(true);
    		return;
    	}
    	if(versions.contains(command.toLowerCase())) {
    		Messages.sendMessage(player, "&7This server is running &dMCT-Spigot &7version 1.7.10-R1.1");
    		Messages.sendMessage(player, "&7(Implementing API 1.8.x) &dcreate exclusively for MCT.");
    		event.setCancelled(true);
    		return;
    	}
    	if(hub.contains(command.toLowerCase())) {
    		Messages.sendMessage(player, "&7This server is running &dMCT-HUB &7version: &f" + HubPlugin.getPlugin().getDescription().getVersion());
    		event.setCancelled(true);
    		return;
    	}
    }
}
