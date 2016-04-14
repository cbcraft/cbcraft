package io.github.cbcraft.common.tileentity;

import io.github.cbcraft.common.block.BlockCode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntityCodeStart extends TileEntity implements ITickable {
	private int blockCodeRunTick = 0;
	private BlockPos blockCodeRunNextPos;
	private IBlockState blockCodeRunNextState;
	private boolean blockCodeReady = false;
	private boolean blockCodeRun = false;
	
	private boolean blockLinked = false;
	private BlockPos blockRobotPos = new BlockPos(0, 0, 0);
	
	public void setBlockCodeRunTick(int tick) {
		blockCodeRunTick = tick;
	}
	
	public int getBlockCodeRunTick() {
		return blockCodeRunTick;
	}
	
	public void setBlockCodeRunNextPos(BlockPos pos) {
		blockCodeRunNextPos = pos;
	}
	
	public BlockPos getBlockCodeRunNextPos() {
		return blockCodeRunNextPos;
	}
	
	public void setBlockCodeRunNextState(IBlockState state) {
		blockCodeRunNextState = state;
	}
	
	public IBlockState getBlockCodeRunNextState() {
		return blockCodeRunNextState;
	}
	
	public void setBlockCodeReady(boolean ready) {
		blockCodeReady = ready;
	}
	
	public boolean getBlockCodeReady() {
		return blockCodeReady;
	}
	
	public void setBlockLinked(boolean linked) {
		blockLinked = linked;
	}
	
	public boolean getBlockLinked() {
		return blockLinked;
	}
	
	public void setBlockRobotPos(BlockPos pos) {
		blockRobotPos = pos;
	}
	
	public BlockPos getBlockRobotPos() {
		return blockRobotPos;
	}
	
	public void setBlockCodeRun(boolean running) {
		blockCodeRun = running;
	}
	
	public boolean getBlockCodeRun() {
		return blockCodeRun;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockLinked);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockRobotPos.getX());
		blockPos.setInteger("y", blockRobotPos.getY());
		blockPos.setInteger("z", blockRobotPos.getZ());
		compound.setTag("robotPos", blockPos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockLinked = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound robotPosNBT = compound.getCompoundTag("robotPos");
		if(robotPosNBT.hasKey("x", NBT_INT_ID) && robotPosNBT.hasKey("y", NBT_INT_ID) && robotPosNBT.hasKey("z", NBT_INT_ID)) {
			blockRobotPos = new BlockPos(robotPosNBT.getInteger("x"), robotPosNBT.getInteger("y"), robotPosNBT.getInteger("z"));
		}
	}
	
	@Override
	public void update() {
		if(!this.worldObj.isRemote) {
			if(blockCodeRun) {
				if(blockCodeRunTick == 0) {
					BlockCode.execBlockCode(this.worldObj, blockCodeRunNextPos, blockCodeRunNextState);
					
					blockCodeRunTick = 10;
				}
				else {
					--blockCodeRunTick;
				}
			}
		}
	}
}
