package ru.openclade.entity;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class BlockBased {
	private int x;
	private int y;
	private int z;
	private String UUID;
	public BlockBased(int x, int y, int z, String UUID) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.UUID = UUID;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	@Override
	public String toString() {
		return ChatColor.RED + "Block [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
}
