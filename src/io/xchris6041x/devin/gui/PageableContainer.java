package io.xchris6041x.devin.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.controls.Button;

public class PageableContainer extends Container {
	
	private Button next;
	private Button prev;
	
	private int page = 0;
	
	public PageableContainer() {
		next = new Button(new ItemStack(Material.GLASS), "Next", (holder, e) -> {
			setPageNumber(++page);
			holder.refresh();
			
			return true;
		});
		prev = new Button(new ItemStack(Material.GLASS), "Previous", (holder, e) -> {
			setPageNumber(--page);
			holder.refresh();
			
			return true;
		});
	}
	
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
		if(page < 0) page = 0;
		else if(page >= getChildren().length) page = getChildren().length - 1;
		
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
		getChildren()[page].render(inventory);
		inventory.setItem(getWidth() * getHeight() - getWidth(), prev.getIcon());
		inventory.setItem(getWidth() * getHeight() - 1, next.getIcon());
	}
	
	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		if(e.getRawSlot() == getWidth() * getHeight() - getWidth()) {
			return prev.onClick(holder, e);
		}
		else if(e.getRawSlot() == getWidth() * getHeight() - 1) {
			return next.onClick(holder, e);
		}
		else {
			return getChildren()[page].onClick(holder, e);
		}
	}
	
	
	@Override
	public boolean isValidChild(Container container) {
		return container instanceof PageContainer;
	}
	
}
