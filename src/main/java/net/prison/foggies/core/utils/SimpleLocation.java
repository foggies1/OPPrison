package net.prison.foggies.core.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.io.Serializable;

public class SimpleLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String worldName;
	@Getter @Setter
	private double x, y, z;
	@Getter @Setter
	private float yaw, pitch;

	public SimpleLocation(World world) {
		this.worldName = world.getName();
	}

	public SimpleLocation(World world, double x, double y, double z, float yaw, float pitch) {
		this.worldName = world.getName();
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public SimpleLocation(World world, double x, double y, double z) {
		this.worldName = world.getName();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SimpleLocation(Location loc) {
		this.worldName = loc.getWorld().getName();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
	}

	public int getBlockX() {
		return (int) this.x;
	}

	public int getBlockY() {
		return (int) this.y;
	}

	public int getBlockZ() {
		return (int) this.z;
	}


	public World getWorld() {
		return Bukkit.getWorld(this.worldName);
	}

	public void setWorld(World world) {
		this.worldName = world.getName();
	}

	public Location toBukkitLocation() {
		return new Location(getWorld(), x, y, z, yaw, pitch);
	}

	public String serialize() throws IOException {
		return SerializeUtils.toString(this);
	}

	public static SimpleLocation deserialize(String serialized) {
		try {
			return (SimpleLocation) SerializeUtils.fromString(serialized);
		} catch (IOException | ClassNotFoundException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public SimpleLocation clone() {
		return new SimpleLocation(this.getWorld(), this.x, this.y, this.z, this.yaw, this.pitch);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SimpleLocation)) {
			return false;
		}

		SimpleLocation loc = (SimpleLocation) obj;
		return loc.worldName.equals(this.worldName) && loc.x == this.x && loc.y == this.y && loc.z == this.z
				&& loc.yaw == this.yaw && loc.pitch == this.pitch;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + (this.worldName != null ? this.worldName.hashCode() : 0);
		hash = 31 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
		hash = 31 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
		hash = 31 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
		return hash;
	}

	@Override
	public String toString() {
		return "{" + this.worldName + ", (" + this.x + ", " + this.y + ", " + this.z + "), " + this.yaw + ", " + this.pitch + "}";
	}

}
