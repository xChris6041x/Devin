package io.xchris6041x.devin.gui;

import io.xchris6041x.devin.gui.controls.Button;
import io.xchris6041x.devin.utils.ItemBuilder;
import io.xchris6041x.devin.utils.ItemDyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for the pagination of containers
 * with automatic next and previous page controls. Those
 * controls are only shown when there are multiple pages.
 *
 * @author Christopher Bishop
 */
public abstract class PageableContainer extends Container {

    private Button next;
    private Button prev;

    private List<PageContainer> pages;

    /**
     * @param nextItem     - The item that represents the next button. The display name is automatically set.
     * @param previousItem - The item that represents the previous button. The display name is automatically set.
     */
    public PageableContainer(ItemStack nextItem, ItemStack previousItem) {
        this.pages = new ArrayList<>();

        nextItem = new ItemBuilder(nextItem)
                .setDisplayName("next")
                .get();

        previousItem = new ItemBuilder(previousItem)
                .setDisplayName("Previous")
                .get();

        next = new Button(nextItem, -1, (holder, e) -> {
            holder.setPage(holder.getPage() + 1);
            holder.refresh();

            return true;
        });
        prev = new Button(previousItem, -1, (holder, e) -> {
            holder.setPage(holder.getPage() - 1);
            holder.refresh();

            return true;
        });
    }

    /**
     * @param button - The item that represents both previous and next buttons. The display names will be set automatically.
     */
    public PageableContainer(ItemStack button) {
        this(button, button);
    }

    /**
     * The next and previous buttons will use black stained glass pane for the icon.
     */
    public PageableContainer() {
        this(new ItemBuilder(Material.STAINED_GLASS_PANE)
                .setColor(ItemDyeColor.BLACK)
                .get());
    }

    protected List<PageContainer> getPages() {
        return pages;
    }

    /**
     * @return whether this container has multiple pages.
     */
    public boolean isMultiPaged() {
        return pages.size() > 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(FrameHolder holder, Inventory inventory) {
        if (next.getPosition() < 0) next.setPosition(getSize() - 1);
        if (prev.getPosition() < 0) prev.setPosition(getSize() - Container.WIDTH);

        pages.get(holder.getPage()).render(holder, inventory);

        if (isMultiPaged()) {
            prev.render(holder, inventory, 0, getSize());
            next.render(holder, inventory, 0, getSize());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean click(FrameHolder holder, InventoryClickEvent e) {
        if (isMultiPaged()) {
            if (next.click(holder, e, 0)) return true;
            if (prev.click(holder, e, 0)) return true;
        }

        return pages.get(holder.getPage()).click(holder, e);
    }

}
