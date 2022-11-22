package com.mcservice.hubcore.manager.type;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.mcservice.hubcore.HubPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class VaultManager {
	
    public Permission permission;
    public Chat chat;
    
    public VaultManager() {
    	this.setup();
	}
    
    public void setup() {
    	this.hookPermission();
    	this.hockChat();
    }
    
    public boolean hockChat() {
    	RegisteredServiceProvider<Chat> chatProvider = (RegisteredServiceProvider<Chat>)HubPlugin.getPlugin().getServer().getServicesManager().getRegistration(Chat.class);
    	if(chatProvider != null) {
    		this.chat = (Chat)chatProvider.getProvider();
    	}
    	return this.chat != null;
    }
    
    public boolean hookPermission() {
        RegisteredServiceProvider<Permission> permissionProvider = (RegisteredServiceProvider<Permission>)HubPlugin.getPlugin().getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            this.permission = (Permission)permissionProvider.getProvider();
        }
        return this.permission != null;
    }
    
    public Permission getPermission() {
        return this.permission;
    }
    
    public Chat getChat() {
    	return this.chat;
    }
}
