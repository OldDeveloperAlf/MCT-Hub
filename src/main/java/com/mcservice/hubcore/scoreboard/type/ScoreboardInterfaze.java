package com.mcservice.hubcore.scoreboard.type;

import java.util.List;
import org.bukkit.entity.Player;

public interface ScoreboardInterfaze {
   List<String> getScoreboardEntries(Player player);
}
