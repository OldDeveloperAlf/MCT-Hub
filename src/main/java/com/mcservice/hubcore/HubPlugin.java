package com.mcservice.hubcore;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcservice.hubcore.manager.ManagerHandler;
import com.mcservice.hubcore.manager.type.ScoreboardManager;
import com.mcservice.hubcore.manager.type.TablistManager;
import com.mcservice.hubcore.providers.ScoreboardProvider;
import com.mcservice.hubcore.providers.TablistProvider;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class HubPlugin extends JavaPlugin implements PluginMessageListener {
	
	private ManagerHandler managerhandler;
	
	public Map<String, Integer> playerCounts;
	public String[] servers; 
	
	public ChatColor color;
	
	@Override
	public void onEnable() {		
		this.playerCounts = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		this.servers = new String[] {"ALL", "HCF", "KitMap", "ComboFly"};
		this.color = ChatColor.LIGHT_PURPLE;
		
		new TablistManager(this, new TablistProvider(), 1000L);
		
		new ScoreboardManager(this, new ScoreboardProvider(), Messages.color("&d&lMCTeams Network"));
		
		this.getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
		
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", (PluginMessageListener) this);
        this.managerhandler = new ManagerHandler(this);
		if(!new File(this.getDataFolder(), "config.yml").exists()) {
			this.saveDefaultConfig();
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				for(String server : servers) {
					ByteArrayDataOutput globalOut = ByteStreams.newDataOutput();
					globalOut.writeUTF("PlayerCount");
                    globalOut.writeUTF(server);
                    Bukkit.getServer().sendPluginMessage((Plugin) HubPlugin.getPlugin(), "BungeeCord", globalOut.toByteArray());
				}
			}
		}.runTaskTimerAsynchronously((Plugin) HubPlugin.getPlugin(), 20L, 20L);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        try {
            if (message.length == 0) {
                return;
            }
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("PlayerCount")) {
                String server = in.readUTF();
                int playerCount = in.readInt();
                this.playerCounts.put(server, playerCount);
            }
        }
        catch (Exception ex) {}
    }
	
	public int getPlayerCount(String server) {
        return this.playerCounts.containsKey(server) ? this.playerCounts.get(server) : 0;
    }
	
	public boolean isOnline(int port) {
		try {
			SocketAddress servers = new InetSocketAddress("localhost", port);
			Socket socket = new Socket();
			
			socket.connect(servers, 1000);
			socket.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static HubPlugin getPlugin() {
		return (HubPlugin) JavaPlugin.getPlugin(HubPlugin.class);
	}
	
	public ManagerHandler getManager() {
		return this.managerhandler;
	}
}
