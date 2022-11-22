package com.mcservice.hubcore.utilities.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import com.mcservice.hubcore.HubPlugin;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class CommandManager {
	
    public void registerCommand(CommandInterface command) {
        registerBukkitCommand(command, command.getName(), command.getAliases());
    }
    
    private void registerBukkitCommand(CommandInterface commandmanager, String name, String... aliases) {
        PluginCommand command = getCommand(name, (Plugin)HubPlugin.getPlugin());
        if (aliases != null) {
            command.setAliases(Arrays.asList(aliases));
        }
        getCommandMap().register(HubPlugin.getPlugin().getDescription().getName(), (Command)command);
        command.setTabCompleter((TabCompleter)new TabCompleter() {
            public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
                List<String> tab = new ArrayList<String>();
                if (tab.isEmpty()) {
                    return null;
                }
                return tab;
            }
        });
        if (commandmanager.getDescription() != null) {
            command.setDescription(commandmanager.getDescription());
        }
        command.setExecutor((sender, command1, label, args) -> {
            if (sender instanceof Player && commandmanager.getAllowedSender() == CommandType.CONSOLE) {
                Messages.sendMessage(sender, "&cYou can not execute this command from the console.");
                return true;
            }
            if (sender instanceof ConsoleCommandSender && commandmanager.getAllowedSender() == CommandType.PLAYER) {
                Messages.sendMessage(sender, "&cOnly players can use this command.");
            }
            if (commandmanager.getPermission() != null) {
                if (!sender.hasPermission(commandmanager.getPermission())) {
                    Messages.sendMessage(sender, "&cYou have not permissions to execute this command.");
                    return true;
                }
            }
            try {
                CommandResult result = commandmanager.execute(sender, args);
                if (result == CommandResult.BAD_USAGE) {
                    Messages.sendMessage(sender, commandmanager.getUsage());
                }
                else if (result == CommandResult.BAD_PERMISSIONS) {
                    Messages.sendMessage(sender, "&cYou have not permissions to execute this command.");
                }
                else if (result == CommandResult.PLAYER_ONLY) {
                    Messages.sendMessage(sender, "&cOnly players can use this command.");
                }
                else if (result == CommandResult.CONSOLE_ONLY) {
                    Messages.sendMessage(sender, "&cYou can not execute this command from the console.");
                }
                else if (result == CommandResult.CRITICAL_ERROR) {
                    Messages.sendMessage(sender, "&cOops... An error occured whilst typing that command, please report this to an admin.");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Messages.sendMessage(sender, "&cOops... An error occured whilst typing that command, please report this to an admin.");
            }
            return true;
        });
    }
    
    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(name, plugin);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
        catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
        catch (InstantiationException e4) {
            e4.printStackTrace();
        }
        catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        catch (NoSuchMethodException e6) {
            e6.printStackTrace();
        }
        return command;
    }
    
    private CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap)f.get(Bukkit.getPluginManager());
            }
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e2) {
            e2.printStackTrace();
        }
        catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
        return commandMap;
    }
}
