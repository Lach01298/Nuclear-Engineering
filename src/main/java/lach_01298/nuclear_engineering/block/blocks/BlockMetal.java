package lach_01298.nuclear_engineering.block.blocks;


import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.util.Constants;
import lach_01298.nuclear_engineering.world.radiation.ManagerRadiation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMetal extends Block implements ItemModelProvider
{
    public static final PropertyEnum<MetalType> TYPE = PropertyEnum.create("metal", MetalType.class);
    public static String name;
    
    public BlockMetal(String name)
    {
        super(Material.IRON);
        this.name=name;
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockMetal.MetalType.COPPER));
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
        return  Item.getItemFromBlock(RegisterBlocks.metal);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getMetadata();
    }
   
  
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (BlockMetal.MetalType metalAmount : BlockMetal.MetalType.values())
        {
            list.add(new ItemStack(itemIn, 1, metalAmount.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(TYPE, BlockMetal.MetalType.META_LOOKUP[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockMetal.MetalType)state.getValue(TYPE)).getMetadata();
    }

   
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

   
    @Override
	public void registerItemModel(Item itemblock)
	{
    	
    	for (BlockMetal.MetalType metalAmount : BlockMetal.MetalType.values())
        {
    		NuclearEngineering.proxy.registerItemRenderer(itemblock,metalAmount.ordinal() , this.name+metalAmount.getName());
        }
	}
    
    
    public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if(state.getValue(TYPE) == MetalType.URANIUM)
		{
			ManagerRadiation radManager = ManagerRadiation.getManager(world);
			radManager.removeRadiationAt(pos, world);
		}
    	super.breakBlock(world, pos, state);
	}
	
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if(state.getValue(TYPE) == MetalType.URANIUM)
		{
			ManagerRadiation radManager = ManagerRadiation.getManager(world);
			radManager.addRadiationAt(pos, world, 8000f, 4000f, 8000f, 0);
		}
	}
    
    
    
    
    public static enum MetalType implements IStringSerializable
    {
        COPPER("copper"),
        TIN("tin"),
        ALUMINIUM("aluminium"),
        LEAD("lead"),
        URANIUM("uranium"),
        HAFNIUM("hafnium");
        
        /** Array of the Block's BlockStates */
        public static final BlockMetal.MetalType[] META_LOOKUP = new BlockMetal.MetalType[values().length];
        
       
        
        private final String name;
        private final String unlocalizedName;
   

    

        private MetalType(String name)
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


        public static BlockMetal.MetalType byMetadata(int meta)
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
            for (BlockMetal.MetalType metal : values())
            {
                META_LOOKUP[metal.getMetadata()] = metal;
            }
        }
    }

	
}