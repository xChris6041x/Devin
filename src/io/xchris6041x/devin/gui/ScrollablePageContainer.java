package io.xchris6041x.devin.gui;

import io.xchris6041x.devin.gui.controls.Button;
import io.xchris6041x.devin.gui.controls.Control;
import io.xchris6041x.devin.utils.ItemBuilder;
import io.xchris6041x.devin.utils.ItemDyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
     *
     * @param scrollUpItem   - The item that represents the scroll up button. The display name is change automatically.
     * @param scrollDownItem - The item that represents the scroll down button. The display name is changed automatically.
     */
    public ScrollablePageContainer(PageableContainer parent, ItemStack scrollUpItem, ItemStack scrollDownItem) {
        super(parent);
        if (parent.getHeight() < 3)
            throw new IllegalArgumentException("Cannot add a ScrollablePageContainer to a PageableContainer with a height less than 3.");

        scrollUpItem = new ItemBuilder(scrollUpItem)
                .setDisplayName("Scroll Up")
                .get();

        scrollDownItem = new ItemBuilder(scrollDownItem)
                .setDisplayName("Scroll Down")
                .get();

        this.up = new Button(scrollUpItem, Container.WIDTH - 1, (holder, e) -> {
            setYOffset(yOffset - 1);
            holder.refresh();

            return true;
        });
        this.down = new Button(scrollDownItem, 0, (holder, e) -> {
            setYOffset(yOffset + 1);
            holder.refresh();

            return true;
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param button - The item which represents both scroll up and down buttons. The display name is changed automatically.
     */
    public ScrollablePageContainer(PageableContainer parent, ItemStack button) {
        this(parent, button, button);
    }

    /**
     * The icon will be black stained glass pane.
     * {@inheritDoc}
     */
    public ScrollablePageContainer(PageableContainer parent) {
        this(parent, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setColor(ItemDyeColor.BLACK).get());
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
        for (Control control : getControlManager().getControls()) {
            if (control.getPosition() > maxPos) {
                maxPos = control.getPosition();
            }
        }

        int y = (int) Math.ceil((maxPos + 1) / (float) Container.WIDTH);
        return Math.max(0, y - getHeight());
    }

    /**
     * Set the y-offset of the controls.
     *
     * @param yOffset - The vertical offset.
     */
    public void setYOffset(int yOffset) {
        int maxYOffset = getMaxYOffset();
        if (yOffset < 0) yOffset = 0;
        else if (yOffset > maxYOffset) yOffset = maxYOffset;

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
    public void render(FrameHolder holder, Inventory inventory) {
        int offset = -yOffset * Container.WIDTH;

        getControlManager().render(holder, inventory, offset, getSize());
        up.render(holder, inventory, 0, getSize());

        down.setPosition(getSize() - 1);
        down.render(holder, inventory, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean click(FrameHolder holder, InventoryClickEvent e) {
        int offset = -yOffset * Container.WIDTH;
        return up.click(holder, e, 0) || down.click(holder, e, 0) || getControlManager().click(holder, e, offset);
    }

}
