package io.xchris6041x.devin.playerdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
