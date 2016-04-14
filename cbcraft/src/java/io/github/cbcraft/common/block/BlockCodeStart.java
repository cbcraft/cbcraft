package io.github.cbcraft.common.block;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import io.github.cbcraft.common.tileentity.TileEntityRobot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCodeStart extends BlockCode {  
	public BlockCodeStart(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	/*public static boolean isCodeRun = false;
	public static Block codeRunBlock;
	public static BlockPos codeRunBlockPos;
	public static IBlockState codeRunBlockState;
	public static int codeRunTick;
	public static BlockPos codeRunBlockStartPos;
	public static IBlockState codeRunBlockStartState;*/
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeStart();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.inventory.getCurrentItem() != null) {
			if(playerIn.inventory.getCurrentItem().getItem() == Items.itemRemote) {
				NBTTagCompound nbtTagCompound = playerIn.inventory.getCurrentItem().getTagCompound();
				
				final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
				final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
				if(nbtTagCompound != null && nbtTagCompound.hasKey("linked", NBT_BOOLEAN_ID) && nbtTagCompound.hasKey("pos", NBT_TAG_ID)) {
					if(!nbtTagCompound.getBoolean("linked")) {
						final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
						NBTTagCompound blockPos = nbtTagCompound.getCompoundTag("pos");
						if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
							TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(pos);
							if(tileEntityCodeStart != null) {
								BlockPos blockRobotPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
								
								tileEntityCodeStart.setBlockRobotPos(blockRobotPos);
								tileEntityCodeStart.setBlockLinked(true);
								
								TileEntityRobot tileEntityRobot = (TileEntityRobot)worldIn.getTileEntity(blockRobotPos);
								if(tileEntityRobot != null) {
									tileEntityRobot.setBlockCodeStartPos(pos);
									
									nbtTagCompound.setBoolean("linked", true);
									
									blockPos = new NBTTagCompound();
									blockPos.setInteger("x", pos.getX());
									blockPos.setInteger("y", pos.getY());
									blockPos.setInteger("z", pos.getZ());
									nbtTagCompound.setTag("pos", blockPos);
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}
