package lach_01298.nuclear_engineering.tile.multiblock;

import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockReactorController;
import lach_01298.nuclear_engineering.network.MessageFluid;
import lach_01298.nuclear_engineering.network.MessageMultiblock;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.tile.machine.TileEntityEnergyFluidMachine;
import net.minecraft.tileentity.TileEntity;

public class TileEntityReactorController extends TileEntityMultiblock
{

	
	
	public void updateTile()
	{
		super.updateTile();
		if(this.getWorld().getBlockState(getPos()).getBlock() == RegisterBlocks.reactorController)
		{
			if(this.getMasterTile() == null)
			{
				this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockReactorController.ON, false));	
			}
			else if(!this.getMasterTile().isMultiblockFormed)
			{
				this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockReactorController.ON, false));
			}

		}
		
		
		
	}
	
	
	
}
