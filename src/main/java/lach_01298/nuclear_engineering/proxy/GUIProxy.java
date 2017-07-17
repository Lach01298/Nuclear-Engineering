package lach_01298.nuclear_engineering.proxy;

//import lach_01298.nuclear_engineering.block.machine.ContainerIso;
//import lach_01298.nuclear_engineering.block.machine.TileEntityIso;
import lach_01298.nuclear_engineering.container.machine.ContainerChemicalReactor;
import lach_01298.nuclear_engineering.container.machine.ContainerGrinder;
import lach_01298.nuclear_engineering.container.ContainerNE;
import lach_01298.nuclear_engineering.gui.GUIChemicalReactor;
import lach_01298.nuclear_engineering.gui.GUIFissionReactor;
import lach_01298.nuclear_engineering.gui.GUIGrinder;
//import lach_01298.nuclear_engineering.gui.GUIIso;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIProxy implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
	
		if (te instanceof TileEntityNE)
		{
			switch (ID)
			{
				case GUI_ID.GRINDER:
				
					return new ContainerGrinder(player.inventory, (TileEntityGrinder) te);
				case GUI_ID.ISOTOPE:
					//return new ContainerIso(player.inventory, (TileEntityIso) te);
				case GUI_ID.CHEMICAL:
					return new ContainerChemicalReactor(player.inventory, (TileEntityChemicalReactor) te);
				case GUI_ID.REACTOR:
					return null; //reactor is just a visual
				default:
					return null;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityNE)
		{
			
			TileEntityNE containerTileEntity = (TileEntityNE) te;
			
			 
			switch (ID)
			{
				case GUI_ID.GRINDER:
					
					return new GUIGrinder(containerTileEntity, new ContainerGrinder(player.inventory, (TileEntityGrinder) containerTileEntity));
				case GUI_ID.ISOTOPE:
				//	return new GUIIso(containerTileEntity, new ContainerIso(player.inventory, (TileEntityIso) containerTileEntity));
				case GUI_ID.CHEMICAL:
					return new GUIChemicalReactor(containerTileEntity, new ContainerChemicalReactor(player.inventory, (TileEntityChemicalReactor) containerTileEntity));
				case GUI_ID.REACTOR:
					
					return new GUIFissionReactor((TileEntityReactorCore) containerTileEntity);
				default:
					return null;
			}

		}
		return null;
	}
}