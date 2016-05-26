package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCodeElse;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCodeElse extends BlockCode {
	public BlockCodeElse(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeElse();
	}
}
