package com.mcservice.hubcore.utilities.protocol;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;

public class ProtocolTabComplete extends PacketAdapter {
	
    public ProtocolTabComplete(Plugin plugin) {
        super(plugin, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE });
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)this);
    }
    
    public void onPacketReceiving(PacketEvent event) {
    	if(event.getPlayer().getName().equals("iSadness_")) {
    		return;
    	}
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            PacketContainer packetContainer = event.getPacket();
            String tab = (String)packetContainer.getStrings().read(0);
            tab = tab.toLowerCase();
            if (tab.startsWith("/") && (tab.length() > 0 || tab.contains(":") || tab.contains("?") || tab.contains("help") || tab.contains("mct-hub") || tab.contains(" ") || tab.startsWith("about") || tab.contains("ver")  || tab.contains("version")  || tab.contains("pex")  || tab.contains("we")  || tab.contains("worldedit")  || tab.contains("permissionsex") || tab.contains("bukkit") || tab.contains("icanhasbukkit"))) {
                event.setCancelled(true);
            }
        }
    }
}
