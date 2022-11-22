package com.mcservice.hubcore.manager;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.manager.command.CommandShutdown;
import com.mcservice.hubcore.manager.command.basic.CommandToggleMessage;
import com.mcservice.hubcore.manager.command.basic.CommandToggleSounds;
import com.mcservice.hubcore.manager.command.basic.chat.CommandMessage;
import com.mcservice.hubcore.manager.command.basic.chat.CommandReply;
import com.mcservice.hubcore.manager.command.queue.CommandJoinQueue;
import com.mcservice.hubcore.manager.command.queue.CommandLeaveQueue;
import com.mcservice.hubcore.manager.listener.EnderButtListener;
import com.mcservice.hubcore.manager.listener.JumpListener;
import com.mcservice.hubcore.manager.listener.PlayerListener;
import com.mcservice.hubcore.manager.listener.SelectorListener;
import com.mcservice.hubcore.manager.listener.SettingsListener;
import com.mcservice.hubcore.manager.type.BasicManager;
import com.mcservice.hubcore.manager.type.QueueManager;
import com.mcservice.hubcore.manager.type.SelectorManager;
import com.mcservice.hubcore.manager.type.SettingsManager;
import com.mcservice.hubcore.manager.type.VaultManager;
import com.mcservice.hubcore.task.BroadcastTask;
import com.mcservice.hubcore.utilities.command.CommandManager;
import com.mcservice.hubcore.utilities.protocol.ProtocolTabComplete;

public class ManagerHandler {
	
	private HubPlugin plugin;
	
	private SelectorManager selectormanager;
	private CommandManager commandmanager;
	private VaultManager vaultmanager;
	private QueueManager queuemanager;
	private BasicManager basicmanager;
	private SettingsManager settingsmanager;
	
	public ManagerHandler(HubPlugin plugin) {
		this.plugin = HubPlugin.getPlugin();
		this.plugin = plugin;
		
		this.selectormanager = new SelectorManager();
		this.commandmanager = new CommandManager();
		this.vaultmanager = new VaultManager();
		this.queuemanager = new QueueManager();
		this.basicmanager = new BasicManager();
		this.settingsmanager = new SettingsManager(this.plugin);
		
		this.registerlisteners();
		this.registercommands();
		this.task();
	}
	
	private void registercommands() {
		this.getCommand().registerCommand(new CommandShutdown(this.plugin));
		this.getCommand().registerCommand(new CommandLeaveQueue());
		this.getCommand().registerCommand(new CommandJoinQueue());
		this.getCommand().registerCommand(new CommandMessage());
		this.getCommand().registerCommand(new CommandReply());
		this.getCommand().registerCommand(new CommandToggleMessage());
		this.getCommand().registerCommand(new CommandToggleSounds());
	}
	
	private void registerlisteners() {
		this.plugin.getServer().getPluginManager().registerEvents((Listener) new SelectorListener(this.plugin), (Plugin)this.plugin);
		this.plugin.getServer().getPluginManager().registerEvents((Listener) new SettingsListener(this.plugin), (Plugin)this.plugin);
		this.plugin.getServer().getPluginManager().registerEvents((Listener) new EnderButtListener(), (Plugin)this.plugin);
		this.plugin.getServer().getPluginManager().registerEvents((Listener) new JumpListener(), (Plugin)this.plugin);
		this.plugin.getServer().getPluginManager().registerEvents((Listener) new PlayerListener(), (Plugin)this.plugin);
		
		new ProtocolTabComplete(this.plugin);
	}
	
	private void task() {
		new BroadcastTask().runTaskTimer((Plugin)this.plugin, 1200L, 1200L);
	}
	
	public SelectorManager getSelectorManager() {
		return this.selectormanager;
	}
	
	public CommandManager getCommand() {
		return this.commandmanager;
	}
	
	public VaultManager getVault() {
		return this.vaultmanager;
	}
	
	public QueueManager getQueueManager() {
		return this.queuemanager;
	}
	
	public BasicManager getBasic() {
		return this.basicmanager;
	}
	
	public SettingsManager getSettingsManager() {
		return this.settingsmanager;
	}
}
