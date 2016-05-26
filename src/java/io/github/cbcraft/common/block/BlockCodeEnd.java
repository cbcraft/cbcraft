package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCodeEnd extends BlockCode {
	public BlockCodeEnd(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCode();
	}
}
