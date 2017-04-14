package io.xchris6041x.devin.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * This is the {@code InventoryHolder} when the frame is open.
 *
 * @author Christopher Bishop
 */
public class FrameHolder implements InventoryHolder {

    private Frame frame;
    private Player p;

    private int page = 0;

    private Inventory inventory;

    /**
     * @param frame - the frame that created the inventory that this holds.
     */
    public FrameHolder(Frame frame, Player player) {
        this.frame = frame;
        this.p = player;
    }

    /**
     * Clear the inventory and render the Frame.
     */
    public void refresh() {
        inventory.clear();
        frame.render(this, inventory);
    }

    /**
     * @return the frame that created the inventory that this holds.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * @return the player that opened the frame.
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * @return the current page that the frame is on starting at 0.
     */
    public int getPage() {
        return page;
    }

    /**
     * Set the page that will be rendered the next refresh.
     *
     * @param page - A page number that starts at 0. This will be clipped automatically to the bounds.
     */
    public void setPage(int page) {
        if (page < 0) page = 0;
        else if (page >= frame.getPages().size()) page = frame.getPages().size() - 1;

        this.page = page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    protected void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
