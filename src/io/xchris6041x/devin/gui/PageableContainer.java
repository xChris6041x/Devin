package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.controls.Button;

public abstract class PageableContainer extends Container {
	
	private Button next;
	private Button prev;
	
	private int page = 0;
	
	private List<PageContainer> pages;
	
	public PageableContainer() {
		this.pages = new ArrayList<PageContainer>();
		
		next = new Button(new ItemStack(Material.GLASS), Container.WIDTH * getHeight() - 1, "Next", (holder, e) -> {
			setPageNumber(++page);
			holder.refresh();
			
			return true;
		});
		prev = new Button(new ItemStack(Material.GLASS), Container.WIDTH * getHeight() - Container.WIDTH, "Previous", (holder, e) -> {
			setPageNumber(--page);
			holder.refresh();
			
			return true;
		});
	}
	
	/**
	 * @return the children of this container.
	 */
	public PageContainer[] getChildren() {
		return pages.toArray(new PageContainer[0]);
	}
	
	/**
	 * Add a page to this container.
	 * @return the page that was created.
	 */
	public PageContainer addPage() {
		PageContainer page = new StaticPageContainer(this);
		pages.add(page);
		
		return page;
	}
	
	/**
	 * Remove a {@code page} from this container.
	 * @param page
	 */
	public void removePage(PageContainer page) {
		pages.remove(page);
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
	public void render(Inventory inventory) {
		getChildren()[page].render(inventory);
		
		prev.render(inventory, 0, Container.WIDTH * getHeight());
		next.render(inventory, 0, Container.WIDTH * getHeight());
	}
	
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		if(e.getRawSlot() == Container.WIDTH * getHeight() - Container.WIDTH) {
			return prev.onClick(holder, e);
		}
		else if(e.getRawSlot() == Container.WIDTH * getHeight() - 1) {
			return next.onClick(holder, e);
		}
		else {
			return getChildren()[page].click(holder, e);
		}
	}
	
}
