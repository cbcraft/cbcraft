package io.github.cbcraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {
	public static String blockCodeUnlocalizedNamePrefix = "code_";
	
	public static Block blockRobot;
	public static Block blockCodeStart;
	public static Block blockCodeMove;
	public static Block blockCodeRotate;
	public static Block blockCodeIf;
	public static Block blockCodeFor;
	public static Block blockCodePlace;
	public static Block blockCodeBreak;
	public static Block blockCodeEnd;
	
	public static void init() {
		GameRegistry.registerBlock(blockRobot = new BlockRobot("robot"), blockRobot.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeStart = new BlockCodeStart(blockCodeUnlocalizedNamePrefix + "start"), blockCodeStart.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeMove = new BlockCode(blockCodeUnlocalizedNamePrefix + "move"), blockCodeMove.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeRotate = new BlockCode(blockCodeUnlocalizedNamePrefix + "rotate"), blockCodeRotate.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeIf = new BlockCode(blockCodeUnlocalizedNamePrefix + "if"), blockCodeIf.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeFor = new BlockCode(blockCodeUnlocalizedNamePrefix + "for"), blockCodeFor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodePlace = new BlockCode(blockCodeUnlocalizedNamePrefix + "place"), blockCodePlace.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeBreak = new BlockCode(blockCodeUnlocalizedNamePrefix + "break"), blockCodeBreak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeEnd = new BlockCode(blockCodeUnlocalizedNamePrefix + "end"), blockCodeEnd.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabBlocks = new CreativeTabs("blocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(blockCodeStart);
		}
	};
}
