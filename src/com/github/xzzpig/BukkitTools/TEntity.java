package com.github.xzzpig.BukkitTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class TEntity {
	private TEntity(){}
	
	public static ArrayList<Player> getNearPlayer(Entity entity,int x,int y,int z) {
		ArrayList<Player> players =new ArrayList<Player>();
		for(Entity entity2:entity.getNearbyEntities(x, y, z)){
			if(entity2.getType().name() == "PLAYER"){
				Player player =(Player)entity2;
				players.add(player);
			}
		}
		return players;
	}
	
	public static void broadMessage(String message){
		@SuppressWarnings("deprecation")
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		for(Player player:players){
			player.sendMessage(message);
		}
	}
	
	public static void broadMessage(String message,Location loc,int distance){
		@SuppressWarnings("deprecation")
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		for(Player player:players){
			if(loc.distance(player.getLocation())<=distance){
				player.sendMessage(message);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Player toPlayer(String name){
		return Bukkit.getPlayer(name);
	}
	
	public static LivingEntity getTarget(Player player,int range) {
		List<Entity> nearbyE = player.getNearbyEntities(range, range, range);
		ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
		for (Entity e : nearbyE) {
			if (e instanceof LivingEntity){
				livingE.add((LivingEntity) e);
			}
		}
		LivingEntity target = null;
		BlockIterator bItr = new BlockIterator(player, range);
		Block block;Location loc;int bx, by, bz;
		double ex, ey, ez;
		while (bItr.hasNext()) {
			block = bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			for (LivingEntity e : livingE) {
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75)&& (bz - .75 <= ez && ez <= bz + 1.75)&& (by - 1 <= ey && ey <= by + 2.5)) {
					target = e;
					break;
				}
			}
		}
		if(target == null)
			target = player;
		return target;
	}
	
	public static LivingEntity getTarget(Player player) {
		return getTarget(player,10);
	}
}
