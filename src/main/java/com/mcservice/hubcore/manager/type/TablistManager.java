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

import com.mcservice.hubcore.tablist.Tablist;
import com.mcservice.hubcore.tablist.type.TablistInterfaze;
import com.mcservice.hubcore.task.UpdateTablistTask;

import lombok.NonNull;

public class TablistManager implements Listener {
	
   public static TablistManager INSTANCE;
   
   public JavaPlugin plugin;
   
   private Map<UUID, Tablist> tablists;
   
   @NonNull private TablistInterfaze supplier;
   
   private int updateTaskId = -1;

   @SuppressWarnings("deprecation")
public TablistManager(@NonNull JavaPlugin plugin, @NonNull TablistInterfaze supplier, long updateTime) {
      if (plugin == null) {
         throw new NullPointerException("plugin");
      } else if (supplier == null) {
         throw new NullPointerException("supplier");
      } else {
         boolean startUpdater = true;
         if (INSTANCE != null) {
            int i;
            for(i = 0; i < 7; ++i) {
               Bukkit.getLogger().warning("");
            }

            Bukkit.getLogger().warning("WARNING! AN INSTANCE OF TABLISTMANAGER ALREADY EXISTS!");
            Bukkit.getLogger().warning("IT IS RECOMMENDED TO ONLY USE ONE OTHERWISE IT CAN CAUSE FLICKERING AND OTHER ISSUES!");

            for(i = 0; i < 7; ++i) {
               Bukkit.getLogger().warning("");
            }
         }

         long remainder = updateTime % 50L;
         if (remainder != 0L) {
            updateTime -= remainder;
            Bukkit.getLogger().info("FIXING UPDATE TIME TO VALID TICK-COUNT...");
         }

         if (updateTime < 50L) {
            startUpdater = false;

            int i;
            for(i = 0; i < 7; ++i) {
               Bukkit.getLogger().warning("");
            }

            Bukkit.getLogger().warning("WARNING! TABLIST UPDATE TASK NOT STARTED!");
            Bukkit.getLogger().warning("REASON: UPDATE TIME IS TOO SHORT.");

            for(i = 0; i < 7; ++i) {
               Bukkit.getLogger().warning("");
            }
         }

         INSTANCE = this;
         this.tablists = new ConcurrentHashMap<UUID, Tablist>();
         this.supplier = supplier;
         this.plugin = plugin;
         if (startUpdater) {
            this.updateTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new UpdateTablistTask(), updateTime / 50L, updateTime / 50L).getTaskId();
         }
         
         Bukkit.getPluginManager().registerEvents(this, plugin);
         Stream.of(Bukkit.getOnlinePlayers()).forEach((player) -> {
            this.getTablist(player, true);
         });
      }
   }

   @Deprecated
   public Tablist getTablist(Player player) {
      return this.getTablist(player, false);
   }

   @Deprecated
   public Tablist getTablist(Player player, boolean create) {
       UUID uniqueId = player.getUniqueId();
       Tablist tablist = (Tablist) this.tablists.get(uniqueId);
       if (tablist == null && create) {
           this.tablists.put(uniqueId, tablist = new Tablist(player));
       }
       return tablist;
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      this.getTablist(player, true);
   }

   @EventHandler
   public void onDisable(PluginDisableEvent event) {
      if (event.getPlugin() == this.plugin) {
         this.tablists.forEach((id, tablist) -> {
            ((Tablist) tablist).hideFakePlayers().clear();
         });
         this.tablists.clear();
         HandlerList.unregisterAll(this);
         if (this.updateTaskId != -1) {
            Bukkit.getScheduler().cancelTask(this.updateTaskId);
         }
      }

   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      UUID uniqueId = player.getUniqueId();
      Tablist tablist;
      if ((tablist = (Tablist)this.tablists.remove(uniqueId)) != null) {
         tablist.hideFakePlayers().clear();
      }

   }

   public JavaPlugin getPlugin() {
      return this.plugin;
   }

   public void setSupplier(@NonNull TablistInterfaze supplier) {
      if (supplier == null) {
         throw new NullPointerException("supplier");
      } else {
         this.supplier = supplier;
      }
   }

   @NonNull
   public TablistInterfaze getSupplier() {
      return this.supplier;
   }
}
