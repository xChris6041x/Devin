package io.xchris6041x.devin;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.CommandRegistrar;
import io.xchris6041x.devin.commands.Commandable;
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
public class Devin extends JavaPlugin implements Commandable {

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
		
		// Setup ObjectParsing
		ObjectParsing.registerParser(String.class, (s) -> { return s; });
	}
	
	@Override
	public void onEnable() {
		CommandRegistrar cr = new CommandRegistrar(this, msgSender);
		cr.registerCommand(this);
	}
	
	@Override
	public void onDisable() {
		try {
			dataManager.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Command(struct = "devin")
	public boolean test(Player p) {
		msgSender.info(p, "This command works!");
		return true;
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
