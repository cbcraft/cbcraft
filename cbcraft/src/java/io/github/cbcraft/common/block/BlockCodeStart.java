package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCodeStart extends BlockCode {  
	public BlockCodeStart(String unlocalizedName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.isBlockContainer = true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeStart();
	}
}
