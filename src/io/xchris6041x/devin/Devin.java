package io.xchris6041x.devin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.commands.ArgumentStream;
import io.xchris6041x.devin.commands.ObjectParsing;
import io.xchris6041x.devin.playerdata.PlayerData;
import io.xchris6041x.devin.playerdata.PlayerDataManager;

/**
 * Main plugin class for DEVIN.
 * @author Christopher Bishop
 */
public class Devin extends JavaPlugin {

	private static Devin instance;
	
	private final MessageSender msgSender = new MessageSender(ChatColor.GREEN + "", ChatColor.RED + "[DEVIN ERROR] ");
	private PlayerDataManager dataManager;
	
	
	@Override
	public void onLoad() {
		if(!getDataFolder().exists()) getDataFolder().mkdir();
		Devin.instance = this;
		
		// Setup YAML and PlayerData.
		ConfigurationSerialization.registerClass(PlayerData.class);
		
		//
		// Setup ObjectParsing
		//
		
		// Primitives
		
		// boolean
		ObjectParsing.registerParser(Boolean.TYPE, (args) -> {
			return Boolean.parseBoolean(args.next());
		});
		// byte
		ObjectParsing.registerParser(Byte.TYPE, (args) -> {
			return Byte.parseByte(args.next());
		});
		// short
		ObjectParsing.registerParser(Short.TYPE, (args) -> {
			return Short.parseShort(args.next());
		});
		// int
		ObjectParsing.registerParser(Integer.TYPE, (args) -> {
			return Integer.parseInt(args.next());
		});
		// float
		ObjectParsing.registerParser(Float.TYPE, (args) -> {
			return Float.parseFloat(args.next());
		});
		// long
		ObjectParsing.registerParser(Long.TYPE, (args) -> {
			return Long.parseLong(args.next());
		});
		// double
		ObjectParsing.registerParser(Double.TYPE, (args) -> {
			return Double.parseDouble(args.next());
		});
		
		// String
		ObjectParsing.registerParser(String.class, (args) -> { return args.next(); });
		// ArgumentStream
		ObjectParsing.registerParser(ArgumentStream.class, (args) -> {
			return args;
		});
		
		// Other usefull objects.
		
		// Player
		ObjectParsing.registerParser(Player.class, (args) -> {
			String s = args.next();
			Player p = Bukkit.getPlayer(s);
			if(p == null) {
				throw new IllegalArgumentException("There is no online player with the name \"" + s + "\"");
			}
			else{
				return p;
			}
		});
	}
	
	@Override
	public void onEnable() {
		dataManager = PlayerDataManager.load(new File(getDataFolder(), "playerdata.yml"));
	}
	
	@Override
	public void onDisable() {
		try {
			dataManager.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Devin getPlugin() {
		return instance;
	}
	
	/**
	 * @return The message sender DEVIN uses.
	 */
	public static MessageSender getMessageSender() {
		return instance.msgSender;
	}
	
	/**
	 * @return The main PlayerDataManager.
	 */
	public static PlayerDataManager getPlayerDataManager() {
		return instance.dataManager;
	}
	
	/**
	 * @param player
	 * @return Get the PlayerData that belongs to a player. If that PlayerData doesn't exist, it is created.
	 */
	public static PlayerData getPlayerData(OfflinePlayer player) {
		return instance.dataManager.getPlayerData(player);
	}
	
}
