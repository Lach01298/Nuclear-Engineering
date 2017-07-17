package lach_01298.nuclear_engineering.block.multiblockPart;

import lach_01298.nuclear_engineering.block.blocks.BlockMetal;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMultiblockPart extends Block implements ITileEntityProvider
{
	
	public BlockMultiblockPart(Material materialIn)
	{
		super(materialIn);
		
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
		{
			TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);
			if(tile.hasMaster())
			{
				tile.getMasterTile().checkMultiBlock(null);
			}
			world.removeTileEntity(pos);
		}
	
	}
	
	
	
	
	
	

	

	public boolean findMaster(World world, BlockPos pos, EntityPlayer player)
	{
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMultiblock();
	}
	
	
	
	

}
