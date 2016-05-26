package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCodeForEnd;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCodeForEnd extends BlockCode {
	public BlockCodeForEnd(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeForEnd();
	}
}
