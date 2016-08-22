package io.xchris6041x.devin.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.xchris6041x.devin.MessageSender;

/**
 * @author Christopher Bishop
 */
public class LayeredCommandExecutor implements CommandExecutor {
	
	private MessageSender msgSender;
	private CommandExecutor executor;
	private Map<List<String>, LayeredCommandExecutor> layers = new HashMap<List<String>, LayeredCommandExecutor>();
	
	
	public LayeredCommandExecutor() { }
	public LayeredCommandExecutor(MessageSender msgSender) {
		this.msgSender = msgSender;
	}
	public LayeredCommandExecutor(CommandExecutor executor, MessageSender msgSender) {
		this.msgSender = msgSender;
		this.executor = executor;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0) {
			// Search through each layer to find a matching layer.
			for(Entry<List<String>, LayeredCommandExecutor> layer : layers.entrySet()) {
				boolean isLayer = false;
				for(String layerLabel : layer.getKey()) {
					if(layerLabel.equalsIgnoreCase(args[0])) {
						isLayer = true;
						break;
					}
				}
				
				if(isLayer) {
					// If a layer matches, build new args without the first element (the sub-command name).
					String[] newArgs = new String[args.length - 1];
					for(int i = 0; i < args.length - 1; i++) {
						newArgs[i] = args[i + 1];
					}
					
					// Execute the next layered command executor with modified label and args.
					return layer.getValue().onCommand(sender, cmd, label + " " + args[0], newArgs);
				}
			}
		}
		
		if(executor == null)
			return false;
		else {
			CommandOptions options = executor.getClass().getAnnotation(CommandOptions.class);
			if(options != null) {
				if(options.onlyPlayers() && !(sender instanceof Player)) {
					return false;
				}
				if(options.onlyOps() && !sender.isOp()) {
					return false;
				}
				if(!options.permission().equals("[NULL]") && !sender.hasPermission(options.permission())) {
					return false;
				}
			}
			return executor.onCommand(sender, cmd, label, args);
		}
	}
	
	
	/**
	 * @return the executer that is used when none of the layers a present.
	 */
	public CommandExecutor getExecutor() {
		return executor;
	}
	
	/**
	 * Set the executor that is used when none of the layers are present.
	 * @param executor - The executor.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor setExecutor(CommandExecutor executor) {
		this.executor = executor;
		return this;
	}
	
	/**
	 * @return the message sender for this command.
	 */
	public MessageSender getMessageSender() {
		return msgSender;
	}
	
	/**
	 * Set the message sender for this command.
	 * @param msgSender
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor setMessageSender(MessageSender msgSender) {
		this.msgSender = msgSender;
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param label - The label the new layer will use when called.
	 * @param executor - The LayredCommandExecutor which will be executed if the first onCommand argument is the label.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(String label, LayeredCommandExecutor executor) {
		layers.put(Arrays.asList(label), executor);
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param labels - Multiple valid labels that can be used to call the new layer.
	 * @param executor - The LayredCommandExecutor which will be executed if the first onCommand argument is any of the labels.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(List<String> labels, LayeredCommandExecutor executor) {
		layers.put(labels, executor);
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param label - The label the new layer will use when called.
	 * @param executor - The CommandExecutor which will be executed when the first argument is the label.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(String label, CommandExecutor executor) {
		layers.put(Arrays.asList(label), new LayeredCommandExecutor(executor, msgSender));
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param labels - Multiple valid labels that can be used to call the new layer..
	 * @param executor - The CommandExecutor which will be executed if the first onCommand argument is any of the labels.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(List<String> label, CommandExecutor executor) {
		layers.put(label, new LayeredCommandExecutor(executor, msgSender));
		return this;
	}
	
	/**
	 * Add a help layer generated by what is currently inside the LayeredCommandExecutor.
	 * @param label - The label used to acces the help command.
	 * @param cmdLabel - The first label used when using the command.
	 * @return
	 */
	public LayeredCommandExecutor addHelpLayer(String label, String cmdLabel) {
		addLayer(label, new HelpCommand(this, msgSender, cmdLabel));
		return this;
	}
	
	
	/**
	 * @return all the layers with their name.
	 */
	public Map<List<String>, LayeredCommandExecutor> getLayerMap() {
		return layers;
	}
	
}
