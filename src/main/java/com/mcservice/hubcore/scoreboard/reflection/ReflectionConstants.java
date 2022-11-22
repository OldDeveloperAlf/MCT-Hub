package com.mcservice.hubcore.scoreboard.reflection;

import java.util.Collection;

public class ReflectionConstants {
	
   public static Class<?> SCOREBOARD_OBJECTIVE_CLASS = Reflection.getMinecraftClass("PacketPlayOutScoreboardObjective");
   
   public static Reflection.ConstructorInvoker SCOREBOARD_OBJECTIVE_CONSTRUCTOR;
   
   public static Reflection.FieldAccessor SCOREBOARD_OBJECTIVE_NAME;
   
   public static Reflection.FieldAccessor SCOREBOARD_OBJECTIVE_TITLE;
   
   public static Reflection.FieldAccessor SCOREBOARD_OBJECTIVE_ACTION;
   
   public static Class<?> SCOREBOARD_DISPLAY_OBJECTIVE_CLASS;
   
   public static Reflection.ConstructorInvoker SCOREBOARD_DISPLAY_OBJECTIVE_CONSTRUCTOR;
   
   public static Reflection.FieldAccessor SCOREBOARD_DISPLAY_OBJECTIVE_NAME;
   
   public static Reflection.FieldAccessor SCOREBOARD_DISPLAY_OBJECTIVE_POSITION;
   
   public static Class<?> SCOREBOARD_TEAM_CLASS;
   
   public static Reflection.ConstructorInvoker SCOREBOARD_TEAM_CONSTRUCTOR;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_NAME;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_DISPLAY_NAME;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_PREFIX;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_SUFFIX;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_PLAYERS;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_ACTION;
   
   public static Reflection.FieldAccessor SCOREBOARD_TEAM_OPTIONS;
   
   public static Class<?> SCOREBOARD_SCORE_CLASS;
   
   public static Reflection.ConstructorInvoker SCOREBOARD_SCORE_CONSTRUCTOR;
   
   public static Reflection.FieldAccessor SCOREBOARD_SCORE_NAME;
   
   public static Reflection.FieldAccessor SCOREBOARD_SCORE_OBJECTIVE;
   
   public static Reflection.FieldAccessor SCOREBOARD_SCORE_SCORE;
   
   public static Reflection.FieldAccessor SCOREBOARD_SCORE_ACTION;

   static {
      SCOREBOARD_OBJECTIVE_CONSTRUCTOR = Reflection.getConstructor(SCOREBOARD_OBJECTIVE_CLASS);
      SCOREBOARD_OBJECTIVE_NAME = Reflection.getField((Class<?>)SCOREBOARD_OBJECTIVE_CLASS, String.class, 0);
      SCOREBOARD_OBJECTIVE_TITLE = Reflection.getField((Class<?>)SCOREBOARD_OBJECTIVE_CLASS, String.class, 1);
      SCOREBOARD_OBJECTIVE_ACTION = Reflection.getField((Class<?>)SCOREBOARD_OBJECTIVE_CLASS, Integer.TYPE, 0);
      SCOREBOARD_DISPLAY_OBJECTIVE_CLASS = Reflection.getMinecraftClass("PacketPlayOutScoreboardDisplayObjective");
      SCOREBOARD_DISPLAY_OBJECTIVE_CONSTRUCTOR = Reflection.getConstructor(SCOREBOARD_DISPLAY_OBJECTIVE_CLASS);
      SCOREBOARD_DISPLAY_OBJECTIVE_NAME = Reflection.getField((Class<?>)SCOREBOARD_DISPLAY_OBJECTIVE_CLASS, String.class, 0);
      SCOREBOARD_DISPLAY_OBJECTIVE_POSITION = Reflection.getField((Class<?>)SCOREBOARD_DISPLAY_OBJECTIVE_CLASS, Integer.TYPE, 0);
      SCOREBOARD_TEAM_CLASS = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");
      SCOREBOARD_TEAM_CONSTRUCTOR = Reflection.getConstructor(SCOREBOARD_TEAM_CLASS);
      SCOREBOARD_TEAM_NAME = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 0);
      SCOREBOARD_TEAM_DISPLAY_NAME = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 1);
      SCOREBOARD_TEAM_PREFIX = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 2);
      SCOREBOARD_TEAM_SUFFIX = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, String.class, 3);
      SCOREBOARD_TEAM_PLAYERS = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Collection.class, 0);
      SCOREBOARD_TEAM_ACTION = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Integer.TYPE, 0);
      SCOREBOARD_TEAM_OPTIONS = Reflection.getField((Class<?>)SCOREBOARD_TEAM_CLASS, Integer.TYPE, 1);
      SCOREBOARD_SCORE_CLASS = Reflection.getMinecraftClass("PacketPlayOutScoreboardScore");
      SCOREBOARD_SCORE_CONSTRUCTOR = Reflection.getConstructor(SCOREBOARD_SCORE_CLASS);
      SCOREBOARD_SCORE_NAME = Reflection.getField((Class<?>)SCOREBOARD_SCORE_CLASS, String.class, 0);
      SCOREBOARD_SCORE_OBJECTIVE = Reflection.getField((Class<?>)SCOREBOARD_SCORE_CLASS, String.class, 1);
      SCOREBOARD_SCORE_SCORE = Reflection.getField((Class<?>)SCOREBOARD_SCORE_CLASS, Integer.TYPE, 0);
      SCOREBOARD_SCORE_ACTION = Reflection.getField((Class<?>)SCOREBOARD_SCORE_CLASS, Integer.TYPE, 1);
   }
}
