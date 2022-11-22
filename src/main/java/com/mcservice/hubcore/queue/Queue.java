package com.mcservice.hubcore.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;

import net.minecraft.util.com.google.common.io.ByteArrayDataOutput;
import net.minecraft.util.com.google.common.io.ByteStreams;

public class Queue {
	
	private String server;
    private List<OfflinePlayer> list;
    private Map<OfflinePlayer, BukkitTask> taskMap;
    private boolean paused;
    private int limit;
    
    public Queue(String server) {
    	this.server = server;
        this.list = new ArrayList<OfflinePlayer>();
        this.taskMap = new HashMap<OfflinePlayer, BukkitTask>();
        this.paused = false;
        this.limit = 400;
        new BukkitRunnable() {
        	
			@Override
			public void run() {
				for(OfflinePlayer target : Queue.this.list) {
					if(target.isOnline()) {
						Player player = (Player)target;
						Messages.sendMessage(player, "&7You are position &a" + Queue.this.getPosition(player) + " &7out of &a" + Queue.this.getSize() + " &7in the server &d" + server + " &7queued.");
					} else {
						Queue.this.list.remove(target);
					}
				}
			}
		}.runTaskTimer((Plugin)HubPlugin.getPlugin(), 160L, 160L);
	}

    public void addEntry(OfflinePlayer p) {
        if (this.list.contains(p)) {
            return;
        }
        if (HubPlugin.getPlugin().getManager().getQueueManager().getPriority(p) == 0) {
            Player o = (Player)p;
            this.sendDirect(o);
            Messages.sendMessage(o, "&7You have been send to &d" + this.server);
            return;
        }
        this.list.add(p);
        for (OfflinePlayer o2 : this.list) {
            int pos = this.list.indexOf(o2);
            if (p != o2 && HubPlugin.getPlugin().getManager().getQueueManager().getPriority(p) < HubPlugin.getPlugin().getManager().getQueueManager().getPriority(o2)) {
                Collections.swap(this.list, pos, this.list.size() - 1);
            }
        }
    }
    
    public void sendFirst() {
        if (!this.list.isEmpty()) {
            Player player = this.list.get(0).getPlayer();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(this.server);
            player.sendPluginMessage((Plugin)HubPlugin.getPlugin(), "BungeeCord", out.toByteArray());
            try {
            	for(Queue queue : HubPlugin.getPlugin().getManager().getQueueManager().queues) {
                	if(queue.getPlayers().contains((OfflinePlayer)player)) {
                		queue.getPlayers().remove((OfflinePlayer)player);
                	}
                }
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
    
    public void sendDirect(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(this.server);
        player.sendPluginMessage((Plugin)HubPlugin.getPlugin(), "BungeeCord", out.toByteArray());
        try {
        	for(Queue queue : HubPlugin.getPlugin().getManager().getQueueManager().queues) {
            	if(queue.getPlayers().contains((OfflinePlayer)player)) {
            		queue.getPlayers().remove((OfflinePlayer)player);
            	}
            }
		} catch (Exception e) { }
    }
    
    public List<OfflinePlayer> getPlayers() {
        return this.list;
    }
    
    public void removeEntry(OfflinePlayer p) {
        this.list.remove(p);
    }
    
    public int getSize() {
        return this.list.size();
    }
    
    public OfflinePlayer getPlayerAt(int i) {
        return this.list.get(i);
    }
    
    public int getPosition(Player p) {
        return this.list.indexOf(p) + 1;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public List<OfflinePlayer> getList() {
        return this.list;
    }
    
    public Map<OfflinePlayer, BukkitTask> getTaskMap() {
        return this.taskMap;
    }
    
    public boolean isPaused() {
        return this.paused;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
    
    public void setList(List<OfflinePlayer> list) {
        this.list = list;
    }
    
    public void setTaskMap(Map<OfflinePlayer, BukkitTask> taskMap) {
        this.taskMap = taskMap;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    protected boolean canEqual(Object other) {
        return other instanceof Queue;
    }
}
