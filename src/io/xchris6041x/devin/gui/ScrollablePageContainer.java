package io.xchris6041x.devin.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.controls.Button;
import io.xchris6041x.devin.gui.controls.Control;

/**
 * A container that can be used as a page on {@link PageableContainer}.
 * This page has a vertical scroll bar on the side that is one slot thick.
 * The scroll up and down controls are provided automatically.
 * 
 * @author Christopher Bishop
 */
public class ScrollablePageContainer extends PageContainer {

	private Button up;
	private Button down;
	
	private int yOffset;
	
	/**
	 * {@inheritDoc}
	 * @param material - The material of the scroll up and down buttons.
	 * @param durability - The durability of the scroll up and down buttons.
	 */
	public ScrollablePageContainer(PageableContainer parent, Material material, short durability) {
		super(parent);
		if(parent.getHeight() < 3) throw new IllegalArgumentException("Cannot add a ScrollablePageContainer to a PageableContainer with a height less than 3.");
		
		this.up = new Button(new ItemStack(material, 1, durability), Container.WIDTH - 1, "Scroll Up", (holder, e) -> {
			setYOffset(yOffset - 1);
			holder.refresh();
			
			return true;
		});
		this.down = new Button(new ItemStack(material, 1, durability), 0, "Scroll Down", (holder, e) -> {
			setYOffset(yOffset + 1);
			holder.refresh();
			
			return true;
		});
	}
	
	/**
	 * {@inheritDoc}
	 * @param material - The material of the scroll up and down buttons. The durability for the item will be 0.
	 */
	public ScrollablePageContainer(PageableContainer parent, Material material) {
		this(parent, material, (short) 0);
	}
	
	/**
	 * The icon that will be used will be black stained glass pane.
	 * {@inheritDoc}
	 */
	public ScrollablePageContainer(PageableContainer parent) {
		this(parent, Material.STAINED_GLASS_PANE, (short) 15);
	}
	
	/**
	 * @return the y-offset of the controls.
	 */
	public int getYOffset() {
		return yOffset;
	}
	
	/**
	 * @return the max y-offset of the controls.
	 */
	public int getMaxYOffset() {
		int maxPos = 0;
		for(Control control : getControlManager().getControls()) {
			if(control.getPosition() > maxPos) {
				maxPos = control.getPosition();
			}
		}
		
		int y = (int) Math.ceil((maxPos + 1) / (float) Container.WIDTH);
		return Math.max(0, y - getHeight());
	}
	
	/**
	 * Set the y-offset of the controls.
	 * @param yOffset
	 */
	public void setYOffset(int yOffset) {
		int maxYOffset = getMaxYOffset();
		if(yOffset < 0) yOffset = 0;
		else if(yOffset > maxYOffset) yOffset = maxYOffset;
		
		this.yOffset = yOffset;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return getParent().isMultiPaged() ? super.getHeight() - 1 : super.getHeight();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Inventory inventory, Player p) {
		int offset = -yOffset * Container.WIDTH;
		
		getControlManager().render(inventory, p, offset, getSize());
		up.render(inventory, p, 0, getSize());
		
		down.setPosition(getSize() - 1);
		down.render(inventory, p, 0, getSize());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		int offset = -yOffset * Container.WIDTH;
		if(up.click(holder, e, 0)) return true;
		if(down.click(holder, e, 0)) return true;
		
		return getControlManager().click(holder, e, offset);
	}

}
