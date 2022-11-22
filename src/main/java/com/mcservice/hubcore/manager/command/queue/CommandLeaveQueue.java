package com.mcservice.hubcore.manager.command.queue;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandResult;
import com.mcservice.hubcore.utilities.command.CommandInterface;

public class CommandLeaveQueue implements CommandInterface {

	@Override
	public String getName() {
		return "leavequeue";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Get out of the queue";
	}

	@Override
	public String[] getUsage() {
		return null;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"queueleave", "lq"};
	}

	@Override
	public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(!(commandsender instanceof Player)) {
			return CommandResult.CONSOLE_ONLY;
		}
		if(arguments.length == 0) {
			Player player = (Player) commandsender;
			if(HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player) == null) {
				Messages.sendMessage(player, "&cYou are not in the queue.");
				return CommandResult.SUCCESS;
			}
			Messages.sendMessage(player, "&cYou are no longer in queue for " + HubPlugin.getPlugin().getManager().getQueueManager().getQueueName((OfflinePlayer)player));
			HubPlugin.getPlugin().getManager().getQueueManager().getQueue((OfflinePlayer)player).removeEntry((OfflinePlayer)player);
			return CommandResult.SUCCESS;
		}
		return CommandResult.SUCCESS;
	}
}
