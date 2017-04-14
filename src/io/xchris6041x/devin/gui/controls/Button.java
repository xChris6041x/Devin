package io.xchris6041x.devin.gui.controls;

import io.xchris6041x.devin.gui.FrameHolder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A control that, when triggered, executes {@link ClickAction#onClick(FrameHolder, InventoryClickEvent)}.
 *
 * @author Christopher Bishop
 */
public class Button extends Control {

    private ClickAction action;

    /**
     * {@inheritDoc}
     *
     * @param action - The action that is called when this control is clicked.
     */
    public Button(IIconBuilder builder, int pos, ClickAction action) {
        super(builder, pos);
        this.action = action;
    }

    /**
     * {@inheritDoc}
     *
     * @param action - The action that is called when this control is clicked.
     */
    public Button(ItemStack icon, int pos, ClickAction action) {
        super(icon, pos);
        this.action = action;
    }

    @Override
    public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
        return action.onClick(holder, e);
    }

}
