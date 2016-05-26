package io.github.cbcraft.common.item;

import net.minecraft.item.Item;

public class ItemWrench extends Item {
	public ItemWrench(String unlocalizedName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(Items.tabItems);
	}
}
