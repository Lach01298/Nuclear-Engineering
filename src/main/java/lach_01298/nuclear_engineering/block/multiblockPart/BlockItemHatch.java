package lach_01298.nuclear_engineering.block.multiblockPart;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.inventory.NEFaces;
import lach_01298.nuclear_engineering.inventory.NEItemStackHandler;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityItemHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityMultiblock;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockItemHatch extends BlockMultiblockPart implements ItemModelProvider
{
	public static final PropertyEnum<HatchType> TYPE = PropertyEnum.create("type", HatchType.class);
    public static String name;
	
	public BlockItemHatch(String name)
	{
		 super(Material.IRON);
	        this.name=name;
	        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockItemHatch.HatchType.INPUT));
	        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	        setRegistryName(name);
			setCreativeTab(NuclearEngineering.NETab);
			setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
			setHardness(Constants.HARDNESS_METAL);
			setResistance(Constants.RESISTANCE_METAL);
	}

	 @Nullable
	    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	    {
	        return  Item.getItemFromBlock(RegisterBlocks.itemHatch);
	    }

	    @Override
	    public int damageDropped(IBlockState state)
	    {
	        return state.getValue(TYPE).getMetadata();
	    }
	   
	  
	    @SideOnly(Side.CLIENT)
	    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	    {
	        for (BlockItemHatch.HatchType item : BlockItemHatch.HatchType.values())
	        {
	            list.add(new ItemStack(itemIn, 1, item.getMetadata()));
	        }
	    }

	    @Override
	    public IBlockState getStateFromMeta(int meta)
	    {
	    	return this.getDefaultState().withProperty(TYPE, BlockItemHatch.HatchType.META_LOOKUP[meta]);
	    }

	    @Override
	    public int getMetaFromState(IBlockState state)
	    {
	        return ((BlockItemHatch.HatchType)state.getValue(TYPE)).getMetadata();
	    }

	   
	    @Override
	    protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, TYPE);
	    }

	   
	    @Override
		public void registerItemModel(Item itemblock)
		{
	    	
	    	for (BlockItemHatch.HatchType item : BlockItemHatch.HatchType.values())
	        {
	    		NuclearEngineering.proxy.registerItemRenderer(itemblock,item.ordinal() , this.name+item.getName());
	        }
		}



	    @Override
		public TileEntity createNewTileEntity(World worldIn, int meta)
		{
			return new TileEntityItemHatch(EnumSlotType.values()[meta]);
		}

	    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
		{
	    	
	    	
	    		TileEntityItemHatch tile = (TileEntityItemHatch)world.getTileEntity(pos);
		    	NEItemStackHandler slot= (NEItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		    	
		    
		    	System.out.println(slot.getStackInSlot(0));
	    	
	    
	    	return true;
		}
	    public void breakBlock(World world, BlockPos pos, IBlockState state)
		{
			
	    	TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileEntityItemHatch)
			{
				
					IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
					
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
			super.breakBlock(world, pos, state);
		}

	public static enum HatchType implements IStringSerializable
	{
	    INPUT("input"),
	    OUTPUT("output");
	    
	    /** Array of the Block's BlockStates */
	    public static final BlockItemHatch.HatchType[] META_LOOKUP = new BlockItemHatch.HatchType[values().length];
	    
	   
	    
	    private final String name;
	    private final String unlocalizedName;




	    private HatchType(String name)
	    {
	    
	        this.name = name;
	        this.unlocalizedName = name;
	       
	    }

	    /**
	     * Returns the EnumType's metadata value.
	     */
	    public int getMetadata()
	    {
	        return this.ordinal();
	    }

	 

	    public String toString()
	    {
	        return this.name;
	    }


	    public static BlockItemHatch.HatchType byMetadata(int meta)
	    {
	        if (meta < 0 || meta >= META_LOOKUP.length)
	        {
	            meta = 0;
	        }

	        return META_LOOKUP[meta];
	    }

	    public String getName()
	    {
	        return this.name;
	    }

	    public String getUnlocalizedName()
	    {
	        return this.unlocalizedName;
	    }

	    static
	    {
	        for (HatchType type : values())
	        {
	            META_LOOKUP[type.getMetadata()] = type;
	        }
	    }
	}
}










