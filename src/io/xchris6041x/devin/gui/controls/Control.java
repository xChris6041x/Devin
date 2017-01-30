package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.xchris6041x.devin.gui.FrameHolder;

public abstract class Control {

	private String text;
	private IIconBuilder builder;
	
	public Control(IIconBuilder builder, String text) {
		this.builder = builder;
		this.text = text;
	}
	public Control(IIconBuilder builder) {
		this(builder, null);
	}
	
	public Control(ItemStack icon, String text) {
		this(new IconBuilder(icon), text);
	}
	public Control(ItemStack icon) {
		this(new IconBuilder(icon));
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
	
	public abstract boolean onClick(FrameHolder holder, InventoryClickEvent e); 
	
}
