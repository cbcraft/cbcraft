package io.github.cbcraft.client.render;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.item.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RenderItems {
	public static void init() {
		register(Items.itemWrench);
		register(Items.itemRemote);
	}
	
	public static void register (Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(CBCraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
