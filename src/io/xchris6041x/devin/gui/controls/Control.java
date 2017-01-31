package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.xchris6041x.devin.gui.FrameHolder;

/**
 * An interactive ItemStack, which contains position information
 * for where it will be in the inventory.
 * 
 * @author Christopher Bishop
 */
public abstract class Control {

	private int pos;
	
	private String text;
	private IIconBuilder builder;
	
	/**
	 * Create a control.
	 * @param builder - What builds the icon.
	 * @param position - The position in the inventory.
	 * @param text - The text of the icon.
	 */
	public Control(IIconBuilder builder, int position, String text) {
		this.builder = builder;
		this.pos = position;
		this.text = text;
	}
	
	/**
	 * Create a control.
	 * @param builder - What builds the icon.
	 * @param position - The position in the inventory.
	 */
	public Control(IIconBuilder builder, int position) {
		this(builder, position, null);
	}
	
	/**
	 * Create a control.
	 * @param icon - The icon that will be used.
	 * @param position - The position in the inventory.
	 * @param text - The text of the icon.
	 */
	public Control(ItemStack icon, int position, String text) {
		this(new IconBuilder(icon), position, text);
	}
	
	/**
	 * Create a control.
	 * @param icon - The icon that will be used.
	 * @param position - The position in the inventory.
	 */
	public Control(ItemStack icon, int position) {
		this(new IconBuilder(icon), position);
	}
	
	/**
	 * @return the position of the control in the inventory.
	 */
	public int getPosition() {
		return pos;
	}
	public void setPosition(int pos) {
		this.pos = pos;
	}
	
	/**
	 * @return the text is the display name of the ItemStack if the
	 * ItemStack does not have a name. If the text is null, no change
	 * will happen to the ItemStack display name.
	 */
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the builder of the icon.
	 */
	public IIconBuilder getIconBuilder() {
		return builder;
	}
	
	/**
	 * @return the icon that will be used when rendering to the inventory.
	 */
	public ItemStack getIcon() {
		ItemStack stack = builder.getIcon();
		
		ItemMeta meta = stack.getItemMeta();
		if(!meta.hasDisplayName() && text != null) {
			meta.setDisplayName(text);
		}
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	/**
	 * Render this control to the inventory {@code inventory} with an offset of {@code offset}.
	 * This control won't be rendered if the {@code position + offset} is less than 0 or greater
	 * than or equal to {@code max}
	 * @param inventory
	 * @param offset
	 * @param max - The upper bounds of the container (exclusive).
	 */
	public void render(Inventory inventory, int offset, int max) {
		int pos = this.pos + offset;
		if(pos >= 0 && pos < max) {
			inventory.setItem(pos, getIcon());
		}
	}
	
	/**
	 * Send the click information to this control. If the slot is correct, then
	 * perform onClick.
	 * @param holder
	 * @param e
	 * @param offset
	 * @return whether the click was consumed. Just because there is a false return doesn't mean there is nothing there.
	 */
	public boolean click(FrameHolder holder, InventoryClickEvent e, int offset) {
		if(e.getRawSlot() == pos + offset) {
			return onClick(holder, e);
		}
		else {
			return false;
		}
	}
	
	protected abstract boolean onClick(FrameHolder holder, InventoryClickEvent e); 
	
}
