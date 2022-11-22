package com.mcservice.hubcore.manager.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.Listener;

public class JumpListener implements Listener {
	
    @EventHandler void onFlightToggle(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            p.setFlying(false);
            p.setAllowFlight(false);
            p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2.5).setY(1.5));
            p.playSound(e.getPlayer().getLocation(), Sound.EXPLODE, 5.0f, 5.0f);
        }
    }
    
    @EventHandler void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getY() != e.getFrom().getY() && p.getGameMode() != GameMode.CREATIVE && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            p.setAllowFlight(true);
        }
    }
}
