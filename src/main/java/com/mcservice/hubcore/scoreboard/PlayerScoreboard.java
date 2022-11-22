package com.mcservice.hubcore.scoreboard;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mcservice.hubcore.manager.type.ScoreboardManager;
import com.mcservice.hubcore.scoreboard.reflection.ReflectionConstants;

import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_7_R4.PlayerConnection;

public class PlayerScoreboard {
	
   public static String[] TEAM_NAMES = new String[15];
   
   private Player player;
   
   private PlayerConnection connection;
   
   private String objectiveName;
   
   private int lastSent = 0;
   
   private boolean valid = true;

   static {
      ChatColor[] colors = ChatColor.values();

      for(int i = 0; i < 15; ++i) {
         ChatColor left = colors[i];
         ChatColor right = colors[15 - i];
         TEAM_NAMES[i] = String.valueOf(left.toString()) + ChatColor.RESET + right.toString() + ChatColor.RESET;
      }

   }

   @SuppressWarnings("unchecked") public PlayerScoreboard(Player player) {
      this.player = player;
      this.connection = ((CraftPlayer)player).getHandle().playerConnection;
      this.objectiveName = "Sidebar-" + RandomStringUtils.random(8, "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789$_?*+-/()[]{}%!=&@<>~");
      PacketPlayOutScoreboardObjective objective = new PacketPlayOutScoreboardObjective();
      ReflectionConstants.SCOREBOARD_OBJECTIVE_NAME.set(objective, this.objectiveName);
      ReflectionConstants.SCOREBOARD_OBJECTIVE_TITLE.set(objective, ScoreboardManager.getInstance().getTitle());
      ReflectionConstants.SCOREBOARD_OBJECTIVE_ACTION.set(objective, Integer.valueOf(0));
      this.connection.sendPacket(objective);
      PacketPlayOutScoreboardDisplayObjective displayObjective = new PacketPlayOutScoreboardDisplayObjective();
      ReflectionConstants.SCOREBOARD_DISPLAY_OBJECTIVE_NAME.set(displayObjective, this.objectiveName);
      ReflectionConstants.SCOREBOARD_DISPLAY_OBJECTIVE_POSITION.set(displayObjective, Integer.valueOf(1));
      this.connection.sendPacket(displayObjective);

      for(int i = 0; i < 15; ++i) {
         PacketPlayOutScoreboardTeam team = new PacketPlayOutScoreboardTeam();
         ReflectionConstants.SCOREBOARD_TEAM_NAME.set(team, TEAM_NAMES[i]);
         ((Collection<String>)ReflectionConstants.SCOREBOARD_TEAM_PLAYERS.get(team)).add(TEAM_NAMES[i]);
         this.connection.sendPacket(team);
      }

   }

   public void send() {
      if (this.valid) {
         List<?> lines = ScoreboardManager.getInstance().getProvider().getScoreboardEntries(this.player);
         int i;
         if (this.lastSent != lines.size()) {
            for(i = 0; i < 15; ++i) {
               PacketPlayOutScoreboardScore score = new PacketPlayOutScoreboardScore();
               ReflectionConstants.SCOREBOARD_SCORE_NAME.set(score, TEAM_NAMES[i]);
               ReflectionConstants.SCOREBOARD_SCORE_OBJECTIVE.set(score, this.objectiveName);
               ReflectionConstants.SCOREBOARD_SCORE_ACTION.set(score, Integer.valueOf(1));
               this.connection.sendPacket(score);
            }
         }

         for(i = 0; i < Math.min(lines.size(), 15); ++i) {
            String line = (String)lines.get(i);
            String left = "";
            String right = "";
            if (line.length() < 17) {
               left = line;
            } else {
               left = line.substring(0, 16);
               right = line.substring(16, line.length());
               if (left.endsWith("§")) {
                  left = left.substring(0, left.length() - 1);
                  right = "§" + right;
               }

               String lastColors = ChatColor.getLastColors(left);
               right = String.valueOf(lastColors) + right;
            }

            PacketPlayOutScoreboardTeam team = new PacketPlayOutScoreboardTeam();
            ReflectionConstants.SCOREBOARD_TEAM_NAME.set(team, TEAM_NAMES[i]);
            ReflectionConstants.SCOREBOARD_TEAM_PREFIX.set(team, left);
            ReflectionConstants.SCOREBOARD_TEAM_SUFFIX.set(team, StringUtils.left(right, 16));
            ReflectionConstants.SCOREBOARD_TEAM_ACTION.set(team, Integer.valueOf(2));
            this.connection.sendPacket(team);
            PacketPlayOutScoreboardScore score2 = new PacketPlayOutScoreboardScore();
            ReflectionConstants.SCOREBOARD_SCORE_NAME.set(score2, TEAM_NAMES[i]);
            ReflectionConstants.SCOREBOARD_SCORE_SCORE.set(score2, 15 - i);
            ReflectionConstants.SCOREBOARD_SCORE_OBJECTIVE.set(score2, this.objectiveName);
            this.connection.sendPacket(score2);
         }

         this.lastSent = lines.size();
      }
   }

   public void clean() {
      for(int i = 0; i < 15; ++i) {
         PacketPlayOutScoreboardTeam team = new PacketPlayOutScoreboardTeam();
         ReflectionConstants.SCOREBOARD_TEAM_NAME.set(team, TEAM_NAMES[i]);
         ReflectionConstants.SCOREBOARD_TEAM_ACTION.set(team, Integer.valueOf(4));
         this.connection.sendPacket(team);
      }

      this.player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
      this.valid = false;
   }
}
