package com.mcservice.hubcore.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.mcservice.hubcore.utilities.chat.ColorType;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class BroadcastTask extends BukkitRunnable {

	private String[] broadcast_message;
	private Integer index;
	
	public BroadcastTask() {
		this.broadcast_message = new String[] {ColorType.LIGHT_PURPLE + " \n Be sure to follow us on Twitter for giveaways and more:\n" + ColorType.B_AQUA + " Twitter.com/@MCTeamsNET \n "};
		this.index = -1;
	}
	
	@Override
	public void run() {
		if(++this.index >= this.broadcast_message.length) {
			this.index = 0;
		}
		String broadcastmessage = this.broadcast_message[this.index];
		Messages.broadcastMessage(broadcastmessage);
	}

}
