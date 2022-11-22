package com.mcservice.hubcore.manager.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.task.ShutdownTask;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandInterface;
import com.mcservice.hubcore.utilities.command.CommandResult;

public class CommandShutdown implements CommandInterface {

	private HubPlugin plugin;
	private ShutdownTask shutdownTask;
	
	public CommandShutdown(HubPlugin plugin) {
		this.plugin = HubPlugin.getPlugin();
		this.plugin = plugin;
	}
	
	@Override
	public String getName() {
		return "shutdown";
	}

	@Override
	public String getPermission() {
		return "mcteams.command.shutdown";
	}

	@Override
	public String getDescription() {
		return "Schedule the server to be shutdown.";
	}

	@Override
	public String[] getUsage() {
		return new String[] {"&cUsage: /" + this.getName() + " <seconds | time | cancel> "};
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(arguments.length == 0) {
			return CommandResult.BAD_USAGE;
		}
		if(arguments[0].equals("time")) {
			if(this.shutdownTask == null) {
				Messages.sendMessage(commandsender, "&cThe server is not scheduled to shut down.");
				return CommandResult.SUCCESS;
			}
			Messages.sendMessage(commandsender, "&6[MCT Bot] &eThe server will shutdown in &d" + this.shutdownTask.getSeconds() + " &eseconds.");
			return CommandResult.SUCCESS;
		}
		if(arguments[0].equals("cancel")) {
			if(this.shutdownTask == null) {
				Messages.sendMessage(commandsender, "&cThe server is not scheduled to shut down.");
				return CommandResult.SUCCESS;
			}
			this.shutdownTask.cancel();
			this.shutdownTask = null;
			Messages.sendMessage(commandsender, "&cThe server shutdown has been canceled.");
			return CommandResult.SUCCESS;
		}
		if(arguments.length == 1) {
			int seconds = 0;
			try {
				seconds = Integer.parseInt(arguments[0]);
			} catch (NumberFormatException e) {
				Messages.sendMessage(commandsender, "&cYou must input a valid number!");
			}
			if(seconds <= 0) {
				Messages.sendMessage(commandsender, "&cYou must input a number greater than 0!");
				return CommandResult.SUCCESS;
			}
			if(this.shutdownTask == null) {
				(this.shutdownTask = new ShutdownTask(seconds)).runTaskTimer((Plugin)this.plugin, 20L, 20L);
				return CommandResult.SUCCESS;
			}
			this.shutdownTask.setSeconds(seconds);
			Messages.sendMessage(commandsender, "&6[MCT Bot] &eThe server will shutdown in &f" + seconds + " &eseconds.");
		}
		return CommandResult.SUCCESS;
	}
}
