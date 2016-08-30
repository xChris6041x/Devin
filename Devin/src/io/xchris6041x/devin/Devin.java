package io.xchris6041x.devin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.commands.CommandRegistrar;
import io.xchris6041x.devin.commands.ObjectParsing;
import io.xchris6041x.devin.mail.AttachableMail;
import io.xchris6041x.devin.mail.Mail;
import io.xchris6041x.devin.mail.MailService;
import io.xchris6041x.devin.playerdata.PlayerData;
import io.xchris6041x.devin.playerdata.PlayerDataManager;

/**
 * Main plugin class for DEVIN.
 * @author Christopher Bishop
 */
public class Devin extends JavaPlugin {

	private static Devin instance;
	
	private final MailService mailService = new MailService();
	private final MessageSender msgSender = new MessageSender(ChatColor.GREEN + "", ChatColor.RED + "[DEVIN ERROR] ");
	private PlayerDataManager dataManager;
	
	@Override
	public void onLoad() {
		if(!getDataFolder().exists()) getDataFolder().mkdir();
		Devin.instance = this;
		
		// Setup YAML and PlayerData.
		ConfigurationSerialization.registerClass(PlayerData.class);
		ConfigurationSerialization.registerClass(Mail.class);
		ConfigurationSerialization.registerClass(AttachableMail.class);
		
		dataManager = PlayerDataManager.load(new File(getDataFolder(), "playerdata.yml"));
		
		//
		// Setup ObjectParsing
		//
		
		// Primitives
		
		// byte
		ObjectParsing.registerParser(Byte.TYPE, (s) -> {
			return Byte.parseByte(s);
		});
		// short
		ObjectParsing.registerParser(Short.TYPE, (s) -> {
			return Short.parseShort(s);
		});
		// int
		ObjectParsing.registerParser(Integer.TYPE, (s) -> {
			return Integer.parseInt(s);
		});
		// float
		ObjectParsing.registerParser(Float.TYPE, (s) -> {
			return Float.parseFloat(s);
		});
		// long
		ObjectParsing.registerParser(Long.TYPE, (s) -> {
			return Long.parseLong(s);
		});
		// double
		ObjectParsing.registerParser(Double.TYPE, (s) -> {
			return Double.parseDouble(s);
		});
		
		// String
		ObjectParsing.registerParser(String.class, (s) -> { return s; });
		
		// Other usefull objects.
		
		// Player
		ObjectParsing.registerParser(Player.class, (s) -> {
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
		CommandRegistrar cr = new CommandRegistrar(this, msgSender);
		cr.registerCommand(new TestCommands());
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
	 * @return the default mail service in Devin.
	 */
	public static MailService getMailService() {
		return instance.mailService;
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
