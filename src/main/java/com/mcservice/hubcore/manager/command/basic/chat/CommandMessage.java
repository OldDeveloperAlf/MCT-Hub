package com.mcservice.hubcore.manager.command.basic.chat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;
import com.mcservice.hubcore.utilities.command.CommandResult;
import com.mcservice.hubcore.utilities.command.CommandInterface;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class CommandMessage implements CommandInterface {

	@Override
	public String getName() {
		return "message";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Messsage an online player.";
	}

	@Override
	public String[] getUsage() {
		return new String[] {"&cUsage: /" + this.getName() + " <playerName> [text...]"};
	}

	@Override
	public String[] getAliases() {
		return new String[] {"msg", "tell", "m", "whisper", "w"};
	}

	@Override
	@Deprecated public CommandResult execute(CommandSender commandsender, String[] arguments) {
		if(!(commandsender instanceof Player)) {
			return CommandResult.CONSOLE_ONLY;
		}
		if(arguments.length < 1) {
			return CommandResult.BAD_USAGE;
		}
		if(arguments.length > 1) {
			Player player = (Player) commandsender;
			Player target = Bukkit.getPlayer(arguments[0]);
			if(target == null) {
				Messages.sendMessage(player, "&cPlayer '" + arguments[0] + "' not found.");
				return CommandResult.SUCCESS;
			}
			if(target.equals(player)) {
				Messages.sendMessage(player, "&cYou cannot send message to yourself.");
				return CommandResult.SUCCESS;
			}
			if(HubPlugin.getPlugin().getManager().getBasic().getToggleMessage().contains(target.getUniqueId())) {
				Messages.sendMessage(player, "&cThis player has private messages disabled.");
				return CommandResult.SUCCESS;
			}
			String message = StringUtils.join((Object[])arguments, ' ', 1, arguments.length);
			Messages.sendMessage(player, "&7(To &r" + HubPlugin.getPlugin().getManager().getVault().getChat().getPlayerPrefix(target) + player.getName() + "&7) &f" + ChatColor.stripColor(message));
			Messages.sendMessage(target, "&7(From &r" + HubPlugin.getPlugin().getManager().getVault().getChat().getPlayerPrefix(player) + player.getName() + "&7) &f" + ChatColor.stripColor(message));
			HubPlugin.getPlugin().getManager().getBasic().getLastMessage().put(player.getName(), target.getName());
			HubPlugin.getPlugin().getManager().getBasic().getLastMessage().put(target.getName(), player.getName());
			if(!HubPlugin.getPlugin().getManager().getBasic().getSounds().contains(target.getUniqueId())) {
				target.playSound(target.getLocation(), Sound.LEVEL_UP, 3F, 3F);
				return CommandResult.SUCCESS;
			}
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(HubPlugin.getPlugin().getManager().getBasic().getSpy().contains(online.getUniqueId())) {
					Messages.sendMessage(online, "&6(SocialSpy) &a" + player.getName() + "&f-> &2" + target.getName() + "&7: &f" + message);
				}
			}
		}
		return null;
	}

}
