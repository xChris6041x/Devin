package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
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
	
	private int page = 0;
	private List<PageContainer> pages;
	
	/**
	 * @param material - The material of the previous and next buttons.
	 * @param durability - The durability of the previous and next buttons.
	 */
	public PageableContainer(Material material, short durability) {
		this.pages = new ArrayList<PageContainer>();
		
		next = new Button(new ItemStack(material, 1, durability), -1, "Next", (holder, e) -> {
			setPageNumber(++page);
			holder.refresh();
			
			return true;
		});
		prev = new Button(new ItemStack(material, 1, durability), -1, "Previous", (holder, e) -> {
			setPageNumber(--page);
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
	 * @return the page number that this container is on.
	 */
	public int getPageNumber() {
		return page;
	}
	
	/**
	 * Set the page number of the page being rendered.
	 * @param page - The page to be set too.
	 */
	public void setPageNumber(int page) {
		if(page < 0) page = 0;
		else if(page >= pages.size()) page = pages.size() - 1;
		
		this.page = page;
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
	public void render(Inventory inventory, Player p) {
		if(next.getPosition() < 0) next.setPosition(getSize() - 1);
		if(prev.getPosition() < 0) prev.setPosition(getSize() - Container.WIDTH);
		
		pages.get(page).render(inventory, p);

		if(isMultiPaged()) {
			prev.render(inventory, p, 0, getSize());
			next.render(inventory, p, 0, getSize());
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
		
		return pages.get(page).click(holder, e);
	}
	
}
