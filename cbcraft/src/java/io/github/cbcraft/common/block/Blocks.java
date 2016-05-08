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
	/*public static Block blockCodeIf;*/
	public static Block blockCodeFor;
	public static Block blockCodeForEnd;
	public static Block blockCodePlace;
	public static Block blockCodeBreak;
	public static Block blockCodeEnd;
	
	public static void init() {
		GameRegistry.registerBlock(blockRobot = new BlockRobot("robot"), blockRobot.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeStart = new BlockCodeStart(blockCodeUnlocalizedNamePrefix + "start"), blockCodeStart.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeMove = new BlockCodeMove(blockCodeUnlocalizedNamePrefix + "move"), blockCodeMove.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeRotate = new BlockCodeRotate(blockCodeUnlocalizedNamePrefix + "rotate"), blockCodeRotate.getUnlocalizedName().substring(5));
		/*GameRegistry.registerBlock(blockCodeIf = new BlockCode(blockCodeUnlocalizedNamePrefix + "if"), blockCodeIf.getUnlocalizedName().substring(5));*/
		GameRegistry.registerBlock(blockCodeFor = new BlockCodeFor(blockCodeUnlocalizedNamePrefix + "for"), blockCodeFor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeForEnd = new BlockCodeForEnd(blockCodeUnlocalizedNamePrefix + "for_end"), blockCodeForEnd.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodePlace = new BlockCodePlace(blockCodeUnlocalizedNamePrefix + "place"), blockCodePlace.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeBreak = new BlockCodeBreak(blockCodeUnlocalizedNamePrefix + "break"), blockCodeBreak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeEnd = new BlockCodeEnd(blockCodeUnlocalizedNamePrefix + "end"), blockCodeEnd.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabBlocks = new CreativeTabs("blocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(blockCodeStart);
		}
	};
}
