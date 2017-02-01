package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.controls.Button;

/**
 * An abstract class for the pagination of containers
 * with automatic next and previous page controls. Those
 * controls are only shown when there are multiple pages.
 * 
 * @author Christopher Bishop
 */
public abstract class PageableContainer extends Container {
	
	private Button next;
	private Button prev;
	
	private List<PageContainer> pages;
	
	/**
	 * @param material - The material of the previous and next buttons.
	 * @param durability - The durability of the previous and next buttons.
	 */
	public PageableContainer(Material material, short durability) {
		this.pages = new ArrayList<PageContainer>();
		
		next = new Button(new ItemStack(material, 1, durability), -1, "Next", (holder, e) -> {
			holder.setPage(holder.getPage() + 1);
			holder.refresh();
			
			return true;
		});
		prev = new Button(new ItemStack(material, 1, durability), -1, "Previous", (holder, e) -> {
			holder.setPage(holder.getPage() - 1);
			holder.refresh();
			
			return true;
		});
	}
	
	/**
	 * @param material - The material of the previous and next buttons. The durability is 0.
	 */
	public PageableContainer(Material material) {
		this(material, (short) 0);
	}
	
	/**
	 * The next and previous buttons will use black stained glass pane for the icon.
	 */
	public PageableContainer() {
		this(Material.STAINED_GLASS_PANE, (short) 15);
	}
	
	protected List<PageContainer> getPages() {
		return pages;
	}

	/**
	 * @return whether this container has multiple pages.
	 */
	public boolean isMultiPaged() {
		return pages.size() > 1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(FrameHolder holder, Inventory inventory) {
		if(next.getPosition() < 0) next.setPosition(getSize() - 1);
		if(prev.getPosition() < 0) prev.setPosition(getSize() - Container.WIDTH);
		
		pages.get(holder.getPage()).render(holder, inventory);

		if(isMultiPaged()) {
			prev.render(holder, inventory, 0, getSize());
			next.render(holder, inventory, 0, getSize());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		if(isMultiPaged()) {
			if(next.click(holder, e, 0)) return true;
			if(prev.click(holder, e, 0)) return true;
		}
		
		return pages.get(holder.getPage()).click(holder, e);
	}
	
}
