package io.xchris6041x.devin.gui.controls;

import io.xchris6041x.devin.gui.FrameHolder;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A functional interface for Controls that have variable click
 * functionality.
 *
 * @author Christopher Bishop
 */
@FunctionalInterface
public interface ClickAction {

    /**
     * Execute when clicked.
     *
     * @param holder - The holder of the frame and inventory.
     * @param e      - The event data.
     * @return whether the click was consumed.
     */
    boolean onClick(FrameHolder holder, InventoryClickEvent e);

}
