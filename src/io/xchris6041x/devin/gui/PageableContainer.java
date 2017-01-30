package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PageableContainer extends Container {
	
	private int page = 0;
	
	/**
	 * Add a page to this container.
	 * @return the page that was created.
	 */
	public PageContainer addPage() {
		PageContainer page = new PageContainer();
		page.setParent(this);
		
		return page;
	}
	
	/**
	 * Remove a {@code page} from this container.
	 * @param page
	 */
	public void removePage(PageContainer page) {
		page.setParent(null);
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
		this.page = page;
	}
	
	@Override
	public int getWidth() {
		return getParent().getWidth();
	}

	@Override
	public int getHeight() {
		return getParent().getHeight();
	}

	
	@Override
	public void render(Inventory inventory) {
		// TODO: Add controls for paging back and forth.
		getChildren()[page].render(inventory);
	}
	
	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		// TODO Trigger controls for paging.
		return getChildren()[page].onClick(holder, e);
	}
	
	
	@Override
	public boolean isValidChild(Container container) {
		return container instanceof PageContainer;
	}
	
}
