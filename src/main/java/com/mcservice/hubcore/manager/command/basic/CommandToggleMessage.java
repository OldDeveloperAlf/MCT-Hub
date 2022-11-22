package com.mcservice.hubcore.manager.command.basic;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandResult;
import com.mcservice.hubcore.utilities.command.CommandInterface;

public class CommandToggleMessage implements CommandInterface {

	@Override
	public String getName() {
		return "togglemessages";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Toggles private messaging";
	}

	@Override
	public String[] getUsage() {
		return null;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"togglepm", "toggleprivatemessages", "privatemessaging"};
	}

	@Override
	public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(!(commandsender instanceof Player)) {
			return CommandResult.CONSOLE_ONLY;
		}
		Player player = (Player) commandsender;
		if(HubPlugin.getPlugin().getManager().getBasic().getToggleMessage().contains(player.getUniqueId())) {
			HubPlugin.getPlugin().getManager().getBasic().getToggleMessage().remove(player.getUniqueId());
			Messages.sendMessage(player, "&eYou will now &aable &eto receive private messages from anyone.");
			return CommandResult.SUCCESS;
		}
		HubPlugin.getPlugin().getManager().getBasic().getToggleMessage().add(player.getUniqueId());
		Messages.sendMessage(player, "&eYou will now &cunable &eto receive private messages from anyone.");
		return CommandResult.SUCCESS;
	}

}
