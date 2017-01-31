package io.xchris6041x.devin.gui.controls;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.xchris6041x.devin.gui.FrameHolder;

/**
 * A class in charge of managing, rendering, and triggering many
 * controls properly.
 * 
 * @author Christopher Bishop
 */
public class ControlManager {

	private List<Control> controls = new ArrayList<Control>();
	
	public List<Control> getControls() {
		return controls;
	}
	

	/**
	 * Render the controls. If two or more controls share the same position, the last
	 * control will be the only one shown.
	 * @param inv
	 * @param offset
	 * @param max
	 */
	public void render(Inventory inv, Player player, int offset, int max) {
		// Render controls.
		for(Control control : controls) {
			control.render(inv, player, offset, max);
		}
	}
	
	/**
	 * Click on a control. If two or more controls share the same position, the first 
	 * control will be the only one clicked on.
	 * @param holder
	 * @param e
	 * @param offset
	 * @return whether a control consumed the click event.
	 */
	public boolean click(FrameHolder holder, InventoryClickEvent e, int offset) {
		// Click on controls
		for(Control control : controls) {
			if(control.click(holder, e, offset)) return true;
		}
		return false;
	}
	
}
