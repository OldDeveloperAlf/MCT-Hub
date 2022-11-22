package com.mcservice.hubcore.task;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mcservice.hubcore.utilities.chat.ColorType;
import com.mcservice.hubcore.utilities.chat.message.Messages;

public class ShutdownTask extends BukkitRunnable {
	
	private List<Integer> times;
	private	int seconds;
	
	public ShutdownTask(int seconds) {
		this.times = Arrays.asList(7200, 3600, 1800, 900, 600, 300, 180, 120, 60, 45, 30, 15, 10, 5, 4, 3, 2, 1);
		this.seconds = seconds;
	}
	
	public List<Integer> getTimes(){
		return this.times;
	}
	
	public int getSeconds() {
		return this.seconds;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	@Override
	@SuppressWarnings("deprecation") public void run() {
		if(this.times.contains(this.seconds)) {
			Messages.broadcastMessage(ColorType.GOLD + "[MCT Bot] " + ColorType.YELLOW + "The server will shutdown in " + ColorType.PINK + this.seconds + ColorType.YELLOW + " seconds.");
		}
		if(this.seconds <= 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Messages.sendMessage(player, "&c[MCT Bot] The server has shut down.");
				Bukkit.shutdown();
			}
		}
		--this.seconds;
	}
}
