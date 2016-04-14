package io.github.cbcraft.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Items {
	public static Item itemWrench;
	public static Item itemRemote;
	
	public static void init() {
		GameRegistry.registerItem(itemWrench = new ItemWrench("wrench"), itemWrench.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemRemote = new ItemRemote("remote"), itemRemote.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabItems = new CreativeTabs("items") {
		@Override
		public Item getTabIconItem() {
			return itemWrench;
		}
	};
}
