package com.mcservice.hubcore.manager.listener;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import com.mcservice.hubcore.utilities.maker.Item;

public class EnderButtListener implements Listener {
	
	@Deprecated
	@EventHandler void onLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player)e.getEntity().getShooter();
            if (e.getEntity() instanceof EnderPearl) {
                Projectile pr = e.getEntity();
                if (pr.getType() == EntityType.ENDER_PEARL) {
                    if (p.getVehicle() != null) {
                        p.getVehicle().remove();
                        p.eject();
                    }
                    p.spigot().setCollidesWithEntities(false);
                    e.setCancelled(true);
                    p.setVelocity(p.getLocation().getDirection().normalize().setY(3.5f));
                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(5.4f));
                    ItemStack enderpearl = new Item(Material.ENDER_PEARL).setTitle("&dEnder Butt &7(Right click to mount)").setLore("&7Use this magical item from the end", "&7to move around the hub in style.").build();
                    p.getInventory().addItem(new ItemStack[] { enderpearl });
                    p.updateInventory();
                    p.setItemInHand(p.getItemInHand());
                }
            }
        }
    }
}
