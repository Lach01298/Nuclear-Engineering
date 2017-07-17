package lach_01298.nuclear_engineering.block.multiblockPart;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.blocks.BlockMetal;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.network.MessageMultiblock;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorController;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockReactorController extends BlockMultiblockPart implements ItemModelProvider
{
	
	
	public static final int searchRadius = 4;
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ON = PropertyBool.create("on");
	private String name;
	public BlockReactorController(String name)
	{
		super(Material.IRON);
		this.name = name;
		
		setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setHardness(Constants.HARDNESS_METAL);
		setResistance(Constants.RESISTANCE_METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ON, false));
	}

	@Override
	public void registerItemModel(Item itemBlock)
	{
		NuclearEngineering.proxy.registerItemRenderer(itemBlock,0 , this.name);
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
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
				else
				{
					TileEntityMultiblock masterTile = tile.getMasterTile();
					if(!(masterTile instanceof TileEntityReactorCore))
					{
						return false;
					}

					if(masterTile.isMultiBlockFormed())
					{
						player.openGui(NuclearEngineering.instance, GUI_ID.REACTOR, world, masterTile.getPos().getX(), masterTile.getPos().getY(), masterTile.getPos().getZ());
						return true;
					}
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
						player.openGui(NuclearEngineering.instance, GUI_ID.REACTOR, world, masterTile.getPos().getX(), masterTile.getPos().getY(), masterTile.getPos().getZ());
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
		return false;
	}
	
	
	@Override
	public boolean findMaster(World world, BlockPos pos, EntityPlayer player)
	{
		if(!world.isRemote)
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

							if(world.getTileEntity(pos.add(x, y, z)) instanceof TileEntityReactorCore)
							{

								TileEntityReactorCore core = (TileEntityReactorCore) world.getTileEntity(pos.add(x, y, z));

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
			System.out.println(world.isRemote);
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID+ ".multiblockNotFormed", "no reator core found"));

		}
		return false;
	}
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(ON, false);
	}
	
	

	public IBlockState getStateFromMeta(int meta)
	{
		if(meta>= 4)
		{
			return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(ON, true);
		}
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(ON, false);	
	}

	public int getMetaFromState(IBlockState state)
	{
		
		if(state.getValue(ON)== true)
		{
			return state.getValue(FACING).getHorizontalIndex()+4;
		}
		return state.getValue(FACING).getHorizontalIndex();
				
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING,ON });
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityReactorController();
	}
	
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
	{
		if(world.getTileEntity(pos) instanceof TileEntityMultiblock)
		{

			TileEntityMultiblock tile = (TileEntityMultiblock) world.getTileEntity(pos);
			if(tile.hasMaster() && !world.isRemote)
			{
				tile.getMasterTile().checkMultiBlock(null);
			}
			
		}
	}
	
	
	
	
}