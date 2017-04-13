package io.xchris6041x.devin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.commands.ArgumentStream;
import io.xchris6041x.devin.commands.ObjectParsing;
import io.xchris6041x.devin.data.UUIDProperty;
import io.xchris6041x.devin.gui.FrameListener;
import io.xchris6041x.devin.injection.InjectedObject;
import io.xchris6041x.devin.injection.Injector;

/**
 * Main plugin class for DEVIN.
 * @author Christopher Bishop
 */
public class Devin extends JavaPlugin {

	private static Devin instance;
	
	private final MessageSender msgSender = new MessageSender(ChatColor.GREEN + "", ChatColor.RED + "[DEVIN ERROR] ");
	private boolean debugMode = true;
	
	private Map<Plugin, Injector> injectors; 
	
	@Override
	public void onLoad() {
		if(!getDataFolder().exists()) getDataFolder().mkdir();

		File config = new File(getDataFolder(), "config.yml");
		if(!config.exists()) saveDefaultConfig();
		
		injectors = new HashMap<>();
		debugMode = getConfig().getBoolean("debug-mode", true);
		Devin.instance = this;
		
		//
		// Setup Configuration Serializations
		//
		ConfigurationSerialization.registerClass(UUIDProperty.class);
		
		//
		// Setup ObjectParsing
		//
		
		// Primitives
		
		// boolean
		ObjectParsing.registerParser(Boolean.TYPE, (args) -> Boolean.parseBoolean(args.next()));
		// byte
		ObjectParsing.registerParser(Byte.TYPE, (args) -> Byte.parseByte(args.next()));
		// short
		ObjectParsing.registerParser(Short.TYPE, (args) -> Short.parseShort(args.next()));
		// int
		ObjectParsing.registerParser(Integer.TYPE, (args) -> Integer.parseInt(args.next()));
		// float
		ObjectParsing.registerParser(Float.TYPE, (args) -> Float.parseFloat(args.next()));
		// long
		ObjectParsing.registerParser(Long.TYPE, (args) -> Long.parseLong(args.next()));
		// double
		ObjectParsing.registerParser(Double.TYPE, (args) -> Double.parseDouble(args.next()));
		
		// String
		ObjectParsing.registerParser(String.class, (args) -> args.next());
		// ArgumentStream
		ObjectParsing.registerParser(ArgumentStream.class, (args) -> args);
		
		// Other useful objects.
		
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
		//
		// Setup Event Listeners
		//
		Bukkit.getPluginManager().registerEvents(new FrameListener(), this);
	}

	
	/**
	 * Print a message to the console if DEVIN is in debug mode.
	 */
	public static void debug(String message) {
		if(instance.debugMode) {
			System.out.println(message);
		}
	}
	
	/**
	 * Print a new line to the console if DEVIN is in debug mode.
	 */
	public static void debug() {
		Devin.debug(" ");
	}
	
	/**
	 * Print a horizontal rule to the console if DEVIN is in debug mode.
	 */
	public static void debugHr() {
		Devin.debug("---------------------------------------------------------------");
	}
	
	/**
	 * @return The message sender DEVIN uses.
	 */
	public static MessageSender getMessageSender() {
		return instance.msgSender;
	}
	
	//
	// Dependency Injection
	//
	
	/**
	 * @param plugin
	 * @return the global Injector for the plugin. If there is none, it creates one and adds the plugin to it with no name. 
	 */
	public static Injector getInjector(Plugin plugin) {
		Injector injector = instance.injectors.get(plugin);
		if(injector == null) {
			injector = new Injector();
			injector.add(plugin);
			
			instance.injectors.put(plugin, injector);
		}
		
		return injector;
	}
	
	//
	// Registering
	//
	/**
	 * Registers the events with Spigot and injects them with the global injector.
	 * @param listener
	 * @param plugin
	 */
	public static void registerEvents(Listener listener, Plugin plugin) {
		getInjector(plugin).inject(listener);
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
	
	/**
	 * Registers the events with Spigot and injects them with the global injector and a MessageSender.
	 * @param plugin
	 * @param msgSender
	 */
	public static void registerEvents(Listener listener, Plugin plugin, MessageSender msgSender) {
		getInjector(plugin).inject(listener, new InjectedObject(msgSender));
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
}
