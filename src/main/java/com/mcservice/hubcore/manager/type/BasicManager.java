package com.mcservice.hubcore.manager.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BasicManager {
	
	private List<UUID> message;
	private List<UUID> social_spy;
	private List<UUID> sounds_message;
	private List<UUID> toggle_message;
	
	private Map<String, String> lastMessage;

	public BasicManager() {
		this.message = new ArrayList<UUID>();
		this.social_spy = new ArrayList<UUID>();
		this.sounds_message = new ArrayList<UUID>();
		this.toggle_message = new ArrayList<UUID>();
		this.lastMessage = new HashMap<String, String>();
	}
	
	public List<UUID> getMessage() {
		return this.message;
	}
	
	public List<UUID> getSpy() {
		return this.social_spy;
	}
	
	public List<UUID> getSounds() {
		return this.sounds_message;
	}
	
	public List<UUID> getToggleMessage() {
		return this.toggle_message;
	}
	
	public Map<String, String> getLastMessage() {
        return this.lastMessage;
    }
}
