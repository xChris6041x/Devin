package io.xchris6041x.devin.playerdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.playerdata.request.PlayerRequest;
import io.xchris6041x.devin.playerdata.request.RequestOptions;

/**
 * A manager that manages PlayerData so it can be easily grabbed.
 * @author Christopher Bishop
 */
public class PlayerDataManager {

	private List<PlayerData> datas;
	private File file;
	
	private PlayerDataManager(List<PlayerData> datas, File file) {
		this.datas = datas;
		this.file = file;
	}
	
	/**
	 * @param player
	 * @return Get the PlayerData that belongs to a player. If that PlayerData doesn't exist, it is created.
	 */
	public PlayerData getPlayerData(OfflinePlayer player) {
		for(PlayerData data : datas) {
			if(data.getOwnerId().equals(player.getUniqueId())) {
				return data;
			}
		}
		
		PlayerData data = new PlayerData(player.getUniqueId());
		datas.add(data);
		return data;
	}
	
	
	public boolean sendRequest(PlayerRequest request) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(request.getSenderUniqueId());
		if(p == null) return false;
		
		PlayerData data = getPlayerData(p);
		data.requests.add(request);
		
		if(p.isOnline()) {
			String message = "";
			String senderName = Bukkit.getOfflinePlayer(request.getSenderUniqueId()).getName();
			
			RequestOptions ro = request.getClass().getAnnotation(RequestOptions.class);
			if(ro == null) {
				message = "Received request from " + senderName + ".";
			}
			else {
				message = "Received " + ro.name().toLowerCase() + " from " + senderName + ".";
			}
			Devin.getMessageSender().info(p.getPlayer(), message);
		}
		
		return true;
	}
	
	
	/**
	 * Load a PlayerDataManager from a YAML file.
	 * @param file
	 * @return the PlayerDataManager that was loaded or an empty PlayerDataManager.
	 */
	@SuppressWarnings("unchecked")
	public static PlayerDataManager load(File file) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return new PlayerDataManager((List<PlayerData>) config.get("datas", new ArrayList<PlayerData>()), file);
	}
	/**
	 * Save the PlayerDataManager to the file it was opened with.
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("datas", datas);
		config.save(file);
	}
	
}
