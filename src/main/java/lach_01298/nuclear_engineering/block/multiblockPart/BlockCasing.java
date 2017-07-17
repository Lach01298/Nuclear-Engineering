package lach_01298.nuclear_engineering.block.multiblockPart;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.network.MessageMultiblock;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblockController;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import lach_01298.nuclear_engineering.util.Constants;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockCasing extends BlockMultiblockPart implements ItemModelProvider
{
	private String name;
	public static final int searchRadius = 2;
	public BlockCasing(String name)
	{
	
		super(Material.IRON);
		this.name = name;
		
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setHardness(Constants.HARDNESS_METAL);
		setResistance(Constants.RESISTANCE_METAL);
	}

	@Override
	public void registerItemModel(Item itemBlock)
	{
		NuclearEngineering.proxy.registerItemRenderer(itemBlock,0 , this.name);
	}
	
	
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(UtilItem.compareItemStacks(heldItem, new ItemStack(Items.STICK)))
		{
			if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
			{
				TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);

				if(world.isRemote)
				{
					if(!tile.hasMaster())
					{
						MessageMultiblock message = new MessageMultiblock(tile);
						NEPacketHandler.INSTANCE.sendToServer(message);
					}
					
					return true;
				}
				else
				{
					
					if(tile.hasMaster())
					{
						TileEntityMultiblock masterTile = tile.getMasterTile();

						if(!(masterTile instanceof TileEntityReactorCore))
						{
							return false;
						}

						if(!tile.getMasterTile().isMultiBlockFormed())
						{
							tile.getMasterTile().checkMultiBlock(player);
						}

						if(masterTile.isMultiBlockFormed())
						{
							tile.getMasterTile().syncData();
							
							return true;
						}
					}
					else
					{
						findMaster(world, pos, player);
						return false;
					}
				}
			}	
		}
		if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
		{
			if(!world.isRemote)
			{
				TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);
				System.out.println(tile.hasMaster());
				System.out.println("pos "+tile.masterPos);
			}
			
		}
		return true;
	}
	
	@Override
	public boolean findMaster(World world, BlockPos pos, EntityPlayer player)
	{

		if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
		{
		
			TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);
			for(int x = -searchRadius; x <= searchRadius; x++)
			{
				for(int y = -searchRadius; y <= searchRadius; y++)
				{
					for(int z = -searchRadius; z <= searchRadius; z++)
					{
						
						if(world.getTileEntity(pos.add(x, y, z)) instanceof TileEntityMultiblockController)
						{
							
							TileEntityMultiblockController core = (TileEntityMultiblockController) world.getTileEntity(pos.add(x, y, z));
							
							if(core.getMasterTile() != null)
							{
								tile.setMasterTile(core.getMasterTile());
								
								tile.getMasterTile().checkMultiBlock(player);
								tile.syncData();
								return true;
							}
							
						}
					}
				}
			}
		}
		player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "no core found"));
		return false;

	}
	
	
	
}
