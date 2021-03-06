package io.xchris6041x.devin.gui.controls;

import io.xchris6041x.devin.gui.Container;
import io.xchris6041x.devin.gui.FrameHolder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * An interactive ItemStack, which contains position information
 * for where it will be in the inventory.
 *
 * @author Christopher Bishop
 */
public abstract class Control {

    private int pos;
    private IIconBuilder builder;

    /**
     * Create a control.
     *
     * @param builder  - What builds the icon.
     * @param position - The position in the inventory.
     */
    public Control(IIconBuilder builder, int position) {
        this.builder = builder;
        this.pos = position;
    }

    /**
     * Create a control.
     *
     * @param icon     - The icon that will be used.
     * @param position - The position in the inventory.
     */
    public Control(ItemStack icon, int position) {
        this(new IconBuilder(icon), position);
    }

    /**
     * @return the position of the control in the inventory.
     */
    public int getPosition() {
        return pos;
    }

    /**
     * Set the position of the control.
     *
     * @param pos - The position of the control.
     */
    public void setPosition(int pos) {
        this.pos = pos;
    }

    /**
     * Set the position of the control using x and y coordinates.
     *
     * @param x - The x value in slots.
     * @param y - The y value in slots.
     */
    public void setPosition(int x, int y) {
        setPosition(x + y * Container.WIDTH);
    }

    /**
     * @return the x value in slots where the control is.
     */
    public int getX() {
        return pos - getY() * Container.WIDTH;
    }

    /**
     * Set the x value of this control.
     *
     * @param x - The x value in slots.
     */
    public void setX(int x) {
        setPosition(x, getY());
    }

    /**
     * @return the y value in slots where the control is.
     */
    public int getY() {
        return (int) Math.floor(pos / (float) Container.WIDTH);
    }

    /**
     * Set the y value of this control
     *
     * @param y - The y value in slots.
     */
    public void setY(int y) {
        setPosition(getX(), y);
    }

    /**
     * @return the builder of the icon.
     */
    public IIconBuilder getIconBuilder() {
        return builder;
    }

    /**
     * Render this control to the inventory {@code inventory} with an offset of {@code offset}.
     * This control won't be rendered if the {@code position + offset} is less than 0 or greater
     * than or equal to {@code max}
     *
     * @param holder    - The holder of the Frame and Inventory.
     * @param inventory - The inventory being rendered on.
     * @param offset    - The offset of the items.
     * @param max       - The upper bounds of the container (exclusive).
     */
    public void render(FrameHolder holder, Inventory inventory, int offset, int max) {
        int pos = this.pos + offset;
        if (pos >= 0 && pos < max) {
            inventory.setItem(pos, builder.getIcon(holder.getPlayer()));
        }
    }

    /**
     * Send the click information to this control. If the slot is correct, then
     * perform onClick.
     *
     * @param holder - The holder of the frame and inventory.
     * @param e      - The click event.
     * @param offset - The offset of the items.
     * @return whether the click was consumed. Just because there is a false return doesn't mean there is nothing there.
     */
    public boolean click(FrameHolder holder, InventoryClickEvent e, int offset) {
        return e.getRawSlot() == pos + offset && onClick(holder, e);
    }

    protected abstract boolean onClick(FrameHolder holder, InventoryClickEvent e);

}
