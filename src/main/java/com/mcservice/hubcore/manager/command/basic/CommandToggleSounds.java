package com.mcservice.hubcore.manager.command.basic;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandResult;
import com.mcservice.hubcore.utilities.command.CommandInterface;

public class CommandToggleSounds implements CommandInterface {

	@Override
	public String getName() {
		return "togglesounds";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Toggles messaging sounds";
	}

	@Override
	public String[] getUsage() {
		return null;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"pmsounds", "togglepmsounds", "messagingsounds"};
	}

	@Override
	public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(!(commandsender instanceof Player)) {
			return CommandResult.CONSOLE_ONLY;
		}
		Player player = (Player) commandsender;
		if(HubPlugin.getPlugin().getManager().getBasic().getSounds().contains(player.getUniqueId())) {
			HubPlugin.getPlugin().getManager().getBasic().getSounds().remove(player.getUniqueId());
			Messages.sendMessage(player, "&eYou will now &aable &eto receive private messages sounds from anyone.");
			return CommandResult.SUCCESS;
		}
		HubPlugin.getPlugin().getManager().getBasic().getSounds().add(player.getUniqueId());
		Messages.sendMessage(player, "&eYou will now &cunable &eto receive private messages sounds from anyone.");
		return CommandResult.SUCCESS;
	}

}
