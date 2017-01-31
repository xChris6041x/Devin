package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.xchris6041x.devin.gui.FrameHolder;

public abstract class Control {

	private int pos;
	
	private String text;
	private IIconBuilder builder;
	
	public Control(IIconBuilder builder, int pos, String text) {
		this.builder = builder;
		this.pos = pos;
		this.text = text;
	}
	public Control(IIconBuilder builder, int pos) {
		this(builder, pos, null);
	}
	
	public Control(ItemStack icon, int pos, String text) {
		this(new IconBuilder(icon), pos, text);
	}
	public Control(ItemStack icon, int pos) {
		this(new IconBuilder(icon), pos);
	}
	
	
	public int getPosition() {
		return pos;
	}
	public void setPosition(int pos) {
		this.pos = pos;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public IIconBuilder getIconBuilder() {
		return builder;
	}
	
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
	 * Render this control to the inventory {@code inv} with an offset of {@code offset}.
	 * This control won't be rendered if the {@code position + offset} is less than 0 or greater
	 * than or equal to {@code max}
	 * @param inv
	 * @param offset
	 * @param max - The upper bounds of the container (exclusive).
	 */
	public void render(Inventory inv, int offset, int max) {
		int pos = this.pos + offset;
		if(pos >= 0 && pos < max) {
			inv.setItem(pos, getIcon());
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
