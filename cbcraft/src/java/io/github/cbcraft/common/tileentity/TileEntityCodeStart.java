package io.github.cbcraft.common.tileentity;

import io.github.cbcraft.common.block.BlockCode;
import io.github.cbcraft.common.block.BlockCodeStart;
import net.minecraft.util.ITickable;

public class TileEntityCodeStart extends TileEntityCode implements ITickable {
	@Override
	public void update() {
		if(!this.worldObj.isRemote) {
			if(BlockCodeStart.isCodeRun) {
				if(BlockCodeStart.codeRunTick == 0) {
					BlockCode.execBlockCode(this.worldObj, BlockCodeStart.codeRunBlockPos, BlockCodeStart.codeRunBlockState);
					
					BlockCodeStart.codeRunTick = 10;
				}
				else {
					--BlockCodeStart.codeRunTick;
				}
			}
		}
	}
}
