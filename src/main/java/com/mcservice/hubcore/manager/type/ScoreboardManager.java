package com.mcservice.hubcore.manager.type;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcservice.hubcore.scoreboard.PlayerScoreboard;
import com.mcservice.hubcore.scoreboard.type.ScoreboardInterfaze;

public class ScoreboardManager implements Listener {
	
   public ScoreboardInterfaze provider;
   
   private JavaPlugin plugin;
   
   public static ScoreboardManager instance;
   
   public boolean debugging;
   
   private String title;
   
   private Map<UUID, PlayerScoreboard> scoreboards;

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      UUID uniqueId = player.getUniqueId();
      Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
         if (this.debugging) {
        	 plugin.getLogger().info("[ScoreboardManager] Loaded scoreboard for " + player.getName() + "[" + player.getUniqueId().toString() + "]");
         }

         this.scoreboards.putIfAbsent(uniqueId, new PlayerScoreboard(player));
      }, 3L);
   }

   public void update() {
      this.scoreboards.forEach((id, board) -> {
         ((PlayerScoreboard) board).send();
      });
   }

   public ScoreboardInterfaze getProvider() {
      return this.provider;
   }

   public String getTitle() {
      return this.title;
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      UUID uniqueId = player.getUniqueId();
      PlayerScoreboard scoreboard;
      if ((scoreboard = (PlayerScoreboard)this.scoreboards.remove(uniqueId)) != null) {
         if (this.debugging) {
        	 this.plugin.getLogger().info("[ScoreboardManager] Deleted scoreboard of " + player.getName() + "[" + player.getUniqueId().toString() + "]");
        	 }

         scoreboard.clean();
      }

   }

   @SuppressWarnings("deprecation") public ScoreboardManager(JavaPlugin plugin, ScoreboardInterfaze provider, String title) {
      instance = this;
      this.plugin = plugin;
      this.provider = provider;
      this.title = title;
      this.scoreboards = new ConcurrentHashMap<UUID, PlayerScoreboard>();
      Bukkit.getPluginManager().registerEvents(this, plugin);
      Stream.of(Bukkit.getOnlinePlayers()).forEach((player) -> {
         if (this.debugging) {
        	 this.plugin.getLogger().info("[ScoreboardManager] Loaded scoreboard for " + player.getName() + "[" + player.getUniqueId().toString() + "]");
         }

         UUID uniqueId = player.getUniqueId();
         this.scoreboards.putIfAbsent(uniqueId, new PlayerScoreboard(player));
      });
      Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 3L, 3L);
   }

   @EventHandler
   public void onDisable(PluginDisableEvent event) {
      if (event.getPlugin() == this.plugin) {
         this.scoreboards.forEach((id, board) -> {
            ((PlayerScoreboard) board).clean();
         });
         this.scoreboards.clear();
         HandlerList.unregisterAll(this);
      }

   }

   public Map<UUID, PlayerScoreboard> getScoreboards() {
      return this.scoreboards;
   }

   public static ScoreboardManager getInstance() {
      return instance;
   }
}
