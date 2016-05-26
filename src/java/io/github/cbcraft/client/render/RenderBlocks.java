package io.github.cbcraft.client.render;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RenderBlocks {
	public static void init() {
		register(Blocks.blockRobot);
		register(Blocks.blockCodeStart);
		register(Blocks.blockCodeMove);
		register(Blocks.blockCodeRotate);
		register(Blocks.blockCodeIf);
		register(Blocks.blockCodeIfElse);
		register(Blocks.blockCodeElse);
		register(Blocks.blockCodeIfEnd);
		register(Blocks.blockCodeFor);
		register(Blocks.blockCodeForEnd);
		register(Blocks.blockCodePlace);
		register(Blocks.blockCodeBreak);
		register(Blocks.blockCodeEnd);
	}
	
	public static void register (Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(CBCraft.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
