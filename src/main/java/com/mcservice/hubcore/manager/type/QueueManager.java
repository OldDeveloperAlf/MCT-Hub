package com.mcservice.hubcore.manager.type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.queue.Queue;

public class QueueManager implements Listener {

	public List<Queue> queues;
	
	public QueueManager() {
		Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)HubPlugin.getPlugin());
		this.queues = new ArrayList<Queue>();
		this.queues.add(new Queue("hcf"));
		this.queues.add(new Queue("kitmap"));
		this.queues.add(new Queue("combofly"));
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					for (final Queue queue : QueueManager.this.queues) {
	                    final int playerCount = HubPlugin.getPlugin().getPlayerCount(queue.getServer());
	                    if (!queue.isPaused() && !queue.getPlayers().isEmpty() && playerCount < queue.getLimit()) {
	                        if (queue.getPlayerAt(0).isOnline()) {
	                        	queue.sendFirst();
	                        }
	                        if (queue.getPlayerAt(0).isOnline()) {
	                            continue;
	                        }
	                        final Player p = (Player)queue.getPlayerAt(0);
	                        if (queue.getPlayers().contains(p)) {
	                        	queue.getPlayers().remove(p);
	                        }
	                        if (!queue.getTaskMap().containsKey(p)) {
	                            continue;
	                        }
	                        queue.getTaskMap().get(p).cancel();
	                        queue.getTaskMap().remove(p);
	                    }
	                }
				} catch (Exception e) { }
			}
		}.runTaskTimer((Plugin)HubPlugin.getPlugin(), 20L, 200L);
	}
	
	public Queue getQueue(OfflinePlayer p) {
        for (Queue q : this.queues) {
            if (q.getPlayers().contains(p)) {
                return q;
            }
        }
        return null;
    }
    
    public Queue getQueue(String s) {
        for (Queue q : this.queues) {
            if (q.getServer().equalsIgnoreCase(s)) {
                return q;
            }
        }
        return null;
    }
    
    public String getQueueName(OfflinePlayer p) {
        return this.getQueue(p).getServer();
    }
    
    public int getPriority(OfflinePlayer target) {
    	return HubPlugin.getPlugin().getConfig().getInt("Rank." + HubPlugin.getPlugin().getManager().getVault().getPermission().getPrimaryGroup((String)null, target) + ".priority");
    }

    @EventHandler void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for (Queue queue : this.queues) {
            if (queue.getPlayers().contains(p)) {
            	queue.removeEntry((OfflinePlayer)p);
            }
        }
    }
}
