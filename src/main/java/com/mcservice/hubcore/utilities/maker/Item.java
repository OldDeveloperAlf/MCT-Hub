package com.mcservice.hubcore.utilities.maker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.mcservice.hubcore.utilities.chat.message.Messages;

public class Item implements Cloneable {
	
    private Material type;
    
    private int data;
    
    private int amount;
    
    private String title;
    
    private List<String> lore;
    
    private Color color;
    
    private HashMap<Enchantment, Integer> enchantments;
    
    private boolean unbreakable;
    
    public Item(Material type) {
        this(type, 1);
    }
    
    public Item(Material type, int amount) {
        this(type, amount, 0);
    }
    
    public Item(Material type, int amount, int data) {
        this.lore = new ArrayList<String>();
        this.type = type;
        this.amount = amount;
        this.data = data;
        this.enchantments = new HashMap<Enchantment, Integer>();
    }
    
    public Item(ItemStack item) {
        this.lore = new ArrayList<String>();
        Validate.notNull((Object)item);
        this.enchantments = new HashMap<Enchantment, Integer>();
        this.type = item.getType();
        this.data = item.getDurability();
        this.amount = item.getAmount();
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasDisplayName()) {
                this.title = item.getItemMeta().getDisplayName();
            }
            if (item.getItemMeta().hasLore()) {
                this.lore = (List<String>)item.getItemMeta().getLore();
            }
        }
        if (item.getEnchantments() != null) {
            this.enchantments.putAll(item.getEnchantments());
        }
        if (item.getType().toString().toLowerCase().contains("leather") && item.getItemMeta() instanceof LeatherArmorMeta) {
            LeatherArmorMeta lam = (LeatherArmorMeta)item.getItemMeta();
            this.color = lam.getColor();
        }
    }
    
    public Item(Item item) {
        this(item.build());
    }
    
    public Item setUnbreakable(boolean flag) {
        this.unbreakable = flag;
        return this;
    }
    
    public Item addLore(String... lore) {
        for (String s : lore) {
            this.lore.add(Messages.color(s));
        }
        return this;
    }
    
    public Item setBase64(String base) {
        return this;
    }
    
    public Item setTexture(String str) {
        return this;
    }
    
    public Item setData(int data) {
        this.data = data;
        return this;
    }
    
    public Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    
    public Item setTitle(String title) {
        this.title = Messages.color(title);
        return this;
    }
    
    public Item setLore(String... lore) {
        this.lore = Messages.color(Arrays.asList(lore));
        return this;
    }
    
    public Item setSkullType(SkullType type) {
        Validate.notNull((Object)type);
        this.setData(type.data);
        return this;
    }
    
    public List<String> getLore() {
        return this.lore;
    }
    
    public Item setLore(List<String> list) {
        this.lore = Messages.color(list);
        return this;
    }
    
    public Material getType() {
        return this.type;
    }
    
    public Item setType(Material type) {
        this.type = type;
        return this;
    }
    
    public Item addEnchantment(Enchantment e, int level) {
        this.enchantments.put(e, level);
        return this;
    }
    
    public Item setColor(Color c) {
        if (!this.type.toString().toLowerCase().contains("leather")) {
            throw new RuntimeException("Cannot set color of non-leather items.");
        }
        this.color = c;
        return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemStack build() {
        Validate.noNullElements(new Object[] { this.type, this.data, this.amount });
        ItemStack stack = new ItemStack(this.type, this.amount, (short)this.data);
        ItemMeta im = stack.getItemMeta();
        if (this.title != null && this.title != "") {
            im.setDisplayName(this.title);
        }
        if (this.lore != null && !this.lore.isEmpty()) {
            im.setLore((List)this.lore);
        }
        if (this.color != null && this.type.toString().toLowerCase().contains("leather")) {
            ((LeatherArmorMeta)im).setColor(this.color);
        }
        stack.setItemMeta(im);
        if (this.enchantments != null && !this.enchantments.isEmpty()) {
            stack.addUnsafeEnchantments((Map)this.enchantments);
        }
        if (this.unbreakable) {
            ItemMeta meta = stack.getItemMeta();
            meta.spigot().setUnbreakable(true);
            stack.setItemMeta(meta);
        }
        return stack;
    }
    
    public Item clone() {
        return new Item(this);
    }
    
    public enum SkullType {
        SKELETON(0), 
        WITHER_SKELETON(1), 
        ZOMBIE(2), 
        PLAYER(3), 
        CREEPER(4);
        
        private int data;
        
        private SkullType(int data) {
            this.data = data;
        }
        
        public int getData() {
            return this.data;
        }
    }
}
