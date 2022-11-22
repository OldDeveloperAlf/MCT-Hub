package com.mcservice.hubcore.utilities.command;

import org.bukkit.command.CommandSender;

public interface CommandInterface {
    String getName();
    
    String getPermission();
    
    String getDescription();
    
    String[] getUsage();
    
    String[] getAliases();
    
    default CommandType getAllowedSender() {
        return CommandType.ALL;
    }
    
    CommandResult execute(CommandSender commandsender, String[] arguments);
}
