package com.mcservice.hubcore.scoreboard.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public class Reflection {
	
   private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
   
   private static String NMS_PREFIX;
   
   private static String VERSION;
   
   private static Pattern MATCH_VARIABLE;

   static {
      NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
      VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
      MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
   }

   public static Reflection.FieldAccessor getField(Class<?> target, String name, Class<?> fieldType) {
      return getField(target, name, fieldType, 0);
   }

   public static Reflection.FieldAccessor getField(String className, String name, Class<?> fieldType) {
      return getField(getClass(className), name, fieldType, 0);
   }

   public static Reflection.FieldAccessor getField(Class<?> target, Class<?> fieldType, int index) {
      return getField(target, (String)null, fieldType, index);
   }

   public static Reflection.FieldAccessor getField(String className, Class<?> fieldType, int index) {
      return getField(getClass(className), fieldType, index);
   }

   private static Reflection.FieldAccessor getField(Class<?> target, String name, Class<?> fieldType, int index) {
      Field[] declaredFields;
      int length = (declaredFields = target.getDeclaredFields()).length;

      for(int i = 0; i < length; ++i) {
         Field field = declaredFields[i];
         if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
            field.setAccessible(true);
            return new Reflection.FieldAccessor() {
               public Object get(Object target) {
                  try {
                     return field.get(target);
                  } catch (IllegalAccessException var3) {
                     throw new RuntimeException("Cannot access reflection.", var3);
                  }
               }

               public void set(Object target, Object value) {
                  try {
                     field.set(target, value);
                  } catch (IllegalAccessException var4) {
                     throw new RuntimeException("Cannot access reflection.", var4);
                  }
               }

               public boolean hasField(Object target) {
                  return field.getDeclaringClass().isAssignableFrom(target.getClass());
               }
            };
         }
      }

      if (target.getSuperclass() != null) {
         return getField(target.getSuperclass(), name, fieldType, index);
      } else {
         throw new IllegalArgumentException("Cannot find field with type " + fieldType);
      }
   }

   public static Reflection.MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
      return getTypedMethod(getClass(className), methodName, (Class<?>)null, params);
   }

   public static Reflection.MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
      return getTypedMethod(clazz, methodName, (Class<?>)null, params);
   }

   public static Reflection.MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
      Method[] declaredMethods;
      int length = (declaredMethods = clazz.getDeclaredMethods()).length;

      for(int i = 0; i < length; ++i) {
         Method method = declaredMethods[i];
         if ((methodName == null || method.getName().equals(methodName)) && (returnType == null || method.getReturnType().equals(returnType)) && Arrays.equals(method.getParameterTypes(), params)) {
            method.setAccessible(true);
            return new Reflection.MethodInvoker() {
               public Object invoke(Object target, Object... arguments) {
                  try {
                     return method.invoke(target, arguments);
                  } catch (Exception var4) {
                     throw new RuntimeException("Cannot invoke method " + method, var4);
                  }
               }
            };
         }
      }

      if (clazz.getSuperclass() != null) {
         return getMethod(clazz.getSuperclass(), methodName, params);
      } else {
         throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
      }
   }

   public static Reflection.ConstructorInvoker getConstructor(String className, Class<?>... params) {
      return getConstructor(getClass(className), params);
   }

   @SuppressWarnings("rawtypes") public static Reflection.ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
      Constructor[] declaredConstructors;
      int length = (declaredConstructors = clazz.getDeclaredConstructors()).length;

      for(int i = 0; i < length; ++i) {
         Constructor<?> constructor = declaredConstructors[i];
         if (Arrays.equals(constructor.getParameterTypes(), params)) {
            constructor.setAccessible(true);
            return new Reflection.ConstructorInvoker() {
               public Object invoke(Object... arguments) {
                  try {
                     return constructor.newInstance(arguments);
                  } catch (Exception var3) {
                     throw new RuntimeException("Cannot invoke constructor " + constructor, var3);
                  }
               }
            };
         }
      }

      throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
   }

   public static Class<?> getUntypedClass(String lookupName) {
      Class<?> clazz = getClass(lookupName);
      return clazz;
   }

   public static Class<?> getClass(String lookupName) {
      return getCanonicalClass(expandVariables(lookupName));
   }

   public static Class<?> getMinecraftClass(String name) {
      return getCanonicalClass(String.valueOf(NMS_PREFIX) + "." + name);
   }

   public static Class<?> getCraftBukkitClass(String name) {
      return getCanonicalClass(String.valueOf(OBC_PREFIX) + "." + name);
   }

   private static Class<?> getCanonicalClass(String canonicalName) {
      try {
         return Class.forName(canonicalName);
      } catch (ClassNotFoundException var2) {
         throw new IllegalArgumentException("Cannot find " + canonicalName, var2);
      }
   }

   private static String expandVariables(String name) {
      StringBuffer output = new StringBuffer();

      Matcher matcher;
      String replacement;
      for(matcher = MATCH_VARIABLE.matcher(name); matcher.find(); matcher.appendReplacement(output, Matcher.quoteReplacement(replacement))) {
         String variable = matcher.group(1);
         replacement = "";
         if ("nms".equalsIgnoreCase(variable)) {
            replacement = NMS_PREFIX;
         } else if ("obc".equalsIgnoreCase(variable)) {
            replacement = OBC_PREFIX;
         } else {
            if (!"version".equalsIgnoreCase(variable)) {
               throw new IllegalArgumentException("Unknown variable: " + variable);
            }

            replacement = VERSION;
         }

         if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
            replacement = String.valueOf(replacement) + ".";
         }
      }

      matcher.appendTail(output);
      return output.toString();
   }

   public interface ConstructorInvoker {
      Object invoke(Object... var1);
   }

   public interface FieldAccessor {
      Object get(Object var1);

      void set(Object var1, Object var2);

      boolean hasField(Object var1);
   }

   public interface MethodInvoker {
      Object invoke(Object var1, Object... var2);
   }
}
