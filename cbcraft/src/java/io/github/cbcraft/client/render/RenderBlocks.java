package io.github.cbcraft.client.render;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RenderBlocks {
	public static void init() {
		registerBlock(Blocks.blockRobot);
		registerBlock(Blocks.blockCodeStart);
		registerBlock(Blocks.blockCodeMove);
		registerBlock(Blocks.blockCodeRotate);
		registerBlock(Blocks.blockCodeIf);
		registerBlock(Blocks.blockCodeFor);
		registerBlock(Blocks.blockCodePlace);
		registerBlock(Blocks.blockCodeBreak);
		registerBlock(Blocks.blockCodeEnd);
	}
	
	public static void registerBlock (Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(CBCraft.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
