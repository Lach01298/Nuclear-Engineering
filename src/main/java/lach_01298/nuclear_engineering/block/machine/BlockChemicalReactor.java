package lach_01298.nuclear_engineering.block.machine;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import lach_01298.nuclear_engineering.inventory.NEFaces;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockChemicalReactor extends BlockContainer implements ItemModelProvider
{
	public final String name = "machineChemicalReactor";
	
	public BlockChemicalReactor()
	{
		super(Material.IRON);
		setHardness(Constants.HARDNESS_ORE);
		setResistance(Constants.RESISTANCE_ORE);
		setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE);
		setCreativeTab(NuclearEngineering.NETab);
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChemicalReactor();
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	
	@Override
	// opens Gui on Right Click
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileEntityChemicalReactor))
		{
			return false;
		}
		player.openGui(NuclearEngineering.instance, GUI_ID.CHEMICAL, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	//drops inventory
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityGrinder)
		{
			for(EnumFacing face : NEFaces.faces)
			{
				IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face);
				for(int i = 0; i < itemHandler.getSlots(); i++)
				{
					ItemStack stack = itemHandler.getStackInSlot(i);
					if(stack != null)
					{
						System.out.println(itemHandler.getStackInSlot(i));
						EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
						world.spawnEntityInWorld(item);
						itemHandler.extractItem(i, stack.stackSize, false);
					}
				}
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	
	@Override
	public void registerItemModel(Item itemblock)
	{
		NuclearEngineering.proxy.registerItemRenderer(itemblock,0 , this.name);
	}

	
}