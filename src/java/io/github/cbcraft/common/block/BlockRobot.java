package io.github.cbcraft.common.block;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.block.material.MaterialRobot;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import io.github.cbcraft.common.tileentity.TileEntityRobot;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRobot extends BlockContainer {
	public BlockRobot(String unlocalizedName) {
		super(MaterialRobot.robot);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(Blocks.tabBlocks);
		this.setBlockBounds(0.125F, 0.125F, 0.125F, 0.875F, 0.875F, 0.875F);
		this.setBlockUnbreakable();
		this.setStepSound(soundTypeWood);
		this.disableStats();
	}
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getHorizontal(meta);
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing)state.getValue(FACING);
		int facingbits = facing.getHorizontalIndex();
		return facingbits;
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING});
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRobot();
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.inventory.getCurrentItem() != null) {
			if(playerIn.inventory.getCurrentItem().getItem() == Items.itemRemote) {
				NBTTagCompound nbtTagCompound = playerIn.inventory.getCurrentItem().getTagCompound();
				if(nbtTagCompound == null) {
					nbtTagCompound = new NBTTagCompound();
					playerIn.inventory.getCurrentItem().setTagCompound(nbtTagCompound);
				}
				nbtTagCompound.setBoolean("linked", false);
				
				NBTTagCompound blockPos = new NBTTagCompound();
				blockPos.setInteger("x", pos.getX());
				blockPos.setInteger("y", pos.getY());
				blockPos.setInteger("z", pos.getZ());
				nbtTagCompound.setTag("pos", blockPos);
				
				if(!worldIn.isRemote) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".remote.link.ready")));
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				if(!worldIn.isRemote) {
					TileEntityRobot tileEntityRobot = (TileEntityRobot)worldIn.getTileEntity(pos);
					if(tileEntityRobot != null && tileEntityRobot.hasBlockCodeStartPos()) {
						TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(tileEntityRobot.getBlockCodeStartPos());
						if(tileEntityCodeStart != null) {
							if(tileEntityCodeStart.getBlockCodeRun()) {
								Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".exec.robotBreak")));
								
								return false;
							}
							
							tileEntityCodeStart.setBlockLinked(false);
							
							worldIn.markBlockForUpdate(tileEntityRobot.getBlockCodeStartPos());
						}
					}
					
					this.dropBlockAsItem(worldIn, pos, state, 0);
					worldIn.setBlockToAir(pos);
				}
			}
		}
		
		return false;
	}
}
