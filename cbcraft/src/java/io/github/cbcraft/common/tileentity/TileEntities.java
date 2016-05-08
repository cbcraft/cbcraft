package io.github.cbcraft.common.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityRobot.class, "robot");
		GameRegistry.registerTileEntity(TileEntityCodeStart.class, "code_start");
		GameRegistry.registerTileEntity(TileEntityCodeBreak.class, "code_break");
		GameRegistry.registerTileEntity(TileEntityCodeMove.class, "code_move");
		GameRegistry.registerTileEntity(TileEntityCodePlace.class, "code_place");
		GameRegistry.registerTileEntity(TileEntityCodeRotate.class, "code_rotate");
		GameRegistry.registerTileEntity(TileEntityCodeFor.class, "code_for");
		GameRegistry.registerTileEntity(TileEntityCodeForEnd.class, "code_for_end");
		GameRegistry.registerTileEntity(TileEntityCode.class, "code_end");
	}
}
