package io.xchris6041x.devin.utils;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@SuppressWarnings("ALL")
public class ItemBuilder {

    protected ItemStack stack;
    protected ItemMeta meta;

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
        this.meta = stack.getItemMeta();
    }

    public ItemBuilder(Material type) {
        this(new ItemStack(type));
    }

    public ItemStack get() {
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }
    
    /**
     * @deprecated No longer works since Spigot 1.13. Use Materials instead.
     */
    @Deprecated
    public ItemBuilder setColor(ItemDyeColor color) {
        setDurability(color.getDurability());
        return this;
    }

    /**
     * @deprecated No longer works since Spigot 1.13. Use Materials instead and ItemMeta instead.
     */
    @Deprecated
    public ItemBuilder setDurability(short durability) {
        stack.setDurability(durability);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }
    
}
