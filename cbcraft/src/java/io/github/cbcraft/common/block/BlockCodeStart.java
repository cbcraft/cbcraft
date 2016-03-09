package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCodeStart extends BlockCode {  
	public BlockCodeStart(String unlocalizedName) {
		super(unlocalizedName);
		this.isBlockContainer = true;
	}
	
	public static boolean isCodeRun = false;
	public static Block codeRunBlock;
	public static BlockPos codeRunBlockPos;
	public static IBlockState codeRunBlockState;
	public static int codeRunTick;
	public static BlockPos codeRunBlockStartPos;
	public static IBlockState codeRunBlockStartState;
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeStart();
	}
}
