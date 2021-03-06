package io.xchris6041x.devin.gui.controls;

import io.xchris6041x.devin.gui.FrameHolder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * A class in charge of managing, rendering, and triggering many
 * controls properly.
 *
 * @author Christopher Bishop
 */
public class ControlManager {

    private List<Control> controls = new ArrayList<>();

    /**
     * @return a list of the controls.
     */
    public List<Control> getControls() {
        return controls;
    }

    /**
     * Render the controls. If two or more controls share the same position, the last
     * control will be the only one shown.
     *
     * @param inv
     * @param offset
     * @param max
     */
    public void render(FrameHolder holder, Inventory inv, int offset, int max) {
        // Render controls.
        for (Control control : controls) {
            control.render(holder, inv, offset, max);
        }
    }

    /**
     * Click on a control. If two or more controls share the same position, the first
     * control will be the only one clicked on.
     *
     * @param holder
     * @param e
     * @param offset
     * @return whether a control consumed the click event.
     */
    public boolean click(FrameHolder holder, InventoryClickEvent e, int offset) {
        // Click on controls
        for (Control control : controls) {
            if (control.click(holder, e, offset)) return true;
        }
        return false;
    }

}
