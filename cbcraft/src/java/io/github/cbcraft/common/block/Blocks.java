package io.github.cbcraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {
	public static String blockCodeUnlocalizedNamePrefix = "code_";
	
	public static Block blockCodeStart;
	
	public static void init() {
		GameRegistry.registerBlock(blockCodeStart = new BlockCodeStart(blockCodeUnlocalizedNamePrefix + "start"), blockCodeStart.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabBlocks = new CreativeTabs("blocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(blockCodeStart);
		}
	};
}
