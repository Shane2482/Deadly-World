package shane2482.deadlyworld.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.IMetaBlockName;
import shane2482.deadlyworld.blocks.base.itemblockenum;

public class block_lamp extends Block implements IMetaBlockName {
	private static final PropertyBool isOn = PropertyBool.create("ison");
	public static final PropertyEnum TYPE = PropertyEnum.create("type", lampType.class);

	public block_lamp(Material material, String name, String regName, float hardness, float resistance, String tool,
			int level) {
		super(material);
		setSoundType(SoundType.GLASS);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, lampType.Clear).withProperty(isOn, Boolean.valueOf(false)));
		setUnlocalizedName(name);
		setRegistryName(regName);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(DeadlyWorld.block);
		register();

	}
	
	
	

	
	public void register() {
		registerBlock(this, new itemblockenum(this));
		for (int i = 0; i < lampType.values().length; ++i) {
			registerRendering();
		}
	}

	protected void registerRendering() {
		for (int i = 0; i < lampType.values().length; i++) {
			DeadlyWorld.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(),
					TYPE.getName() + "=" + lampType.values()[i].name);
		}
	}

	public static void registerBlock(Block block, itemblockenum itemblock) {
		GameRegistry.register(block);
		GameRegistry.register(itemblock.setRegistryName(block.getRegistryName()));

	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE, isOn });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		lampType type = (lampType) state.getValue(TYPE);
		return type.ID;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, lampType.values()[meta]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < lampType.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return lampType.values()[stack.getItemDamage()].getName();
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            state = state.cycleProperty(isOn);
            worldIn.setBlockState(pos, state, 3);
            float f = ((Boolean)state.getValue(isOn)).booleanValue() ? 0.6F : 0.5F;
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            worldIn.notifyNeighborsOfStateChange(pos, this);
           
            return true;
        }
    }
	
	 @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
	    {	 
		 double d0 = (double)pos.getX() + 0.5D;
	        double d1 = (double)pos.getY() + 0.65D;
	        double d2 = (double)pos.getZ() + 0.5D;
	        double d3 = 0.22D;
	        double d4 = 0.27D;
	        if(!state.getValue(isOn).FALSE){
	        	worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
	        	worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
	        }
	       
	    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (worldIn.isBlockPowered(pos)){
        	worldIn.setBlockState(pos, state.withProperty(isOn, Boolean.valueOf(true)));
        	
        }
        else{
        	worldIn.setBlockState(pos, state.withProperty(isOn, Boolean.valueOf(false)));
        	
        }
    }
	
	@Override
    public int getLightValue(IBlockState state) {
        return state.getValue(isOn) ? 15 : 0;
    }
	


	public static enum lampType implements IStringSerializable {

		Black(0, "black" ),
		Blue(1, "blue" ),
		Brown(2, "brown" ),
		Clear(3, "clear" ),
		Cyan(4, "cyan" ),
		Gray(5, "gray" ),
		Green(6, "green" ),
		Light_Blue(7, "light_blue" ),
		Lime(8, "lime" ),
		Magenta(9, "magenta" ),
		Orange(10, "orange" ),
		Pink(11, "pink" ),
		Purple(12, "purple" ),
		Red(13, "red" ),
		Silver(14, "silver" ),
		White(15, "white" );
		

		private String name;
		private int ID;

		private lampType(int ID, String name) {
			this.name = name;
			this.ID = ID;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
