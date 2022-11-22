package com.mcservice.hubcore.manager.command.queue;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandResult;
import com.mcservice.hubcore.utilities.command.CommandInterface;

public class CommandJoinQueue implements CommandInterface {

	@Override
	public String getName() {
		return "queuejoin";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Connect to the servers queue";
	}

	@Override
	public String[] getUsage() {
		return new String[] {"&cUsage: /" + this.getName() + " <queue>"};
	}

	@Override
	public String[] getAliases() {
		return new String[] {"join", "play"};
	}

	@Override
	public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(!(commandsender instanceof Player)) {
			return CommandResult.CONSOLE_ONLY;
		}
		Player player = (Player) commandsender;
		if(arguments.length < 1) {
			return CommandResult.BAD_USAGE;
		}
		if(arguments.length > 0) {
			String server = arguments[0];
			if(HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player) != null) {
				Messages.sendMessage(player, "&cYou are already queueing for " + HubPlugin.getPlugin().getManager().getQueueManager().getQueueName((OfflinePlayer)player));
				return CommandResult.SUCCESS;
			}
			if(HubPlugin.getPlugin().getManager().getQueueManager().getQueue(server) == null) {
				Messages.sendMessage(player, "&cThis queue seems to be offline - try again later.");
				return CommandResult.SUCCESS;
			}
			Messages.sendMessage(player, "&eYou have joined the queue for &6" + server);
			HubPlugin.getPlugin().getManager().getQueueManager().getQueue(server).addEntry((OfflinePlayer)player);
			return CommandResult.SUCCESS;
		}
		return CommandResult.SUCCESS;
	}
}
