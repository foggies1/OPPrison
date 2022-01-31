package net.prison.foggies.core.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class PersistentData {

    private static Plugin plugin;
    private PersistentDataContainer persistentDataContainer;
    private ItemStack itemStack;
    private NamespacedKey key;
    private Player player;
    private ItemMeta itemMeta;

    public PersistentData(String key, Player player) {
        this.key = new NamespacedKey(plugin, key);
        this.player = player;
        if (player != null)
            this.persistentDataContainer = player.getPersistentDataContainer();
    }

    public PersistentData(String key, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.key = new NamespacedKey(plugin, key);
        if (itemStack.getItemMeta() != null) {
            this.itemMeta = itemStack.getItemMeta();
            this.persistentDataContainer = this.itemMeta.getPersistentDataContainer();
        }
    }

    public PersistentData(ItemStack itemStack) {
        this.itemStack = itemStack;
        if (itemStack.getItemMeta() != null) {
            this.itemMeta = itemStack.getItemMeta();
            this.persistentDataContainer = this.itemMeta.getPersistentDataContainer();
        }
    }

    public PersistentData setKey(NamespacedKey key){
        this.key = key;
        return this;
    }

    public void removeKey(){
        persistentDataContainer.getKeys().remove(this.key);
    }

    public boolean containsKey(){
        return persistentDataContainer.getKeys().contains(this.key);
    }

    public Set<NamespacedKey> getKeys(){
        return persistentDataContainer.getKeys();
    }

    public void takeByte(byte input) {
        setByte((byte) (getByte() - input));
    }

    public void takeFloat(float input) {
        setFloat(getFloat() - input);
    }

    public void takeLong(long input) {
        setLong(getLong() - input);
    }

    public void takeDouble(double input) {
        setDouble(getDouble() - input);
    }

    public void takeInt(int input) {
        setInt(getInt() - input);
    }

    public void addByte(byte input) {
        setByte((byte) (getByte() + input));
    }

    public void addFloat(float input) {
        setFloat(getFloat() + input);
    }

    public void addLong(long input) {
        setLong(getLong() + input);
    }

    public void addDouble(double input) {
        setDouble(getDouble() + input);
    }

    public void addInt(int input) {
        setInt(getInt() + input);
    }

    public void setString(String input) {
        persistentDataContainer.set(this.key, PersistentDataType.STRING, input);
        save();
    }

    public void setByte(byte input) {
        persistentDataContainer.set(this.key, PersistentDataType.BYTE, input);
        save();
    }

    public void setFloat(float input) {
        persistentDataContainer.set(this.key, PersistentDataType.FLOAT, input);
        save();
    }

    public void setLong(long input) {
        persistentDataContainer.set(this.key, PersistentDataType.LONG, input);
        save();
    }

    public void setDouble(double input) {
        persistentDataContainer.set(this.key, PersistentDataType.DOUBLE, input);
        save();
    }

    public void setInt(int input) {
        persistentDataContainer.set(this.key, PersistentDataType.INTEGER, input);
        save();
    }

    public byte getByte() {
        return persistentDataContainer.has(this.key, PersistentDataType.BYTE)
                ? persistentDataContainer.get(this.key, PersistentDataType.BYTE)
                : -1;
    }

    public String getString() {
        return persistentDataContainer.has(this.key, PersistentDataType.STRING)
                ? persistentDataContainer.get(this.key, PersistentDataType.STRING)
                : "";
    }

    public float getFloat() {
        return persistentDataContainer.has(this.key, PersistentDataType.FLOAT)
                ? persistentDataContainer.get(this.key, PersistentDataType.FLOAT)
                : -1F;
    }

    public long getLong() {
        return persistentDataContainer.has(this.key, PersistentDataType.LONG)
                ? persistentDataContainer.get(this.key, PersistentDataType.LONG)
                : -1L;
    }

    public double getDouble() {
        return persistentDataContainer.has(this.key, PersistentDataType.DOUBLE)
                ? persistentDataContainer.get(this.key, PersistentDataType.DOUBLE)
                : -1D;
    }

    public int getInt() {
        return persistentDataContainer.has(this.key, PersistentDataType.INTEGER)
                ? persistentDataContainer.get(this.key, PersistentDataType.INTEGER)
                : -1;
    }

    public void save() {
        if (this.itemStack != null)
            this.itemStack.setItemMeta(this.itemMeta);
    }

    public static void setPlugin(Plugin pl){
        plugin = pl;
    }

}
