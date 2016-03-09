package io.github.cbcraft.common.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityCodeStart.class, "code_start");
	}
}
