package shane2482.deadlyworld.blocks.machines;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.itemblocks;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityFurnaceBasic;
import shane2482.deadlyworld.init.ModBlocks;
import shane2482.deadlyworld.library.GuiHandler;

public class block_furnace_basic extends Block implements ITileEntityProvider {
	
	protected static final AxisAlignedBB NORTH_FURNACE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	protected static final AxisAlignedBB SOUTH_FURNACE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	protected static final AxisAlignedBB WEST_FURNACE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	protected static final AxisAlignedBB EAST_FURNACE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	

	public block_furnace_basic(Material material, String name, String regName, float hardness, float resistance, String tool, int level, boolean isBurning) {
		super(material);		
		setUnlocalizedName(name);
		setRegistryName(regName);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		register();

	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFurnaceBasic();
	}

	// Register

	private void register() {
		registerBlock(this, getItemBlocks());

		registerRendering();
	}

	public static void registerBlock(Block block, itemblocks itemBlock) {

		GameRegistry.register(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
	}

	protected itemblocks getItemBlocks() {
		return new itemblocks(this);
	}

	protected void registerRendering() {
		DeadlyWorld.proxy.addRenderRegister(new ItemStack(this), getRegistryName(), "inventory");
	}

	// Render

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

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		TileEntityFurnaceBasic te = (TileEntityFurnaceBasic) worldIn.getTileEntity(pos);
		if (te != null && te.isSmelting()){
			EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.4D - 0.3D;

			if (rand.nextDouble() < 0.1D) {
				worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D,
						SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			switch (enumfacing) {
			case WEST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 1D, d2 + d4, 0.0D, 0.0D, 0.0D,
						new int[0]);
				break;
			case EAST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 1D, d2 + d4, 0.0D, 0.0D, 0.0D,
						new int[0]);

				break;
			case NORTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + 1D, d2, 0.0D, 0.0D, 0.0D,
						new int[0]);

				break;
			case SOUTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + 1D, d2, 0.0D, 0.0D, 0.0D,
						new int[0]);

			}
		}
	}

	// Bounding Box
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return source.getBlockState(pos.north()).getBlock() == this ? NORTH_FURNACE_AABB
				: (source.getBlockState(pos.south()).getBlock() == this ? SOUTH_FURNACE_AABB
						: (source.getBlockState(pos.west()).getBlock() == this ? WEST_FURNACE_AABB
								: EAST_FURNACE_AABB));
	}

	// Facing/BlockState

	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		setDefaultFacing(world, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityFurnaceBasic) {
				((TileEntityFurnaceBasic) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	// Function
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && !player.isSneaking()) {
			player.openGui(DeadlyWorld.instance, GuiHandler.Furnace_Basic, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	

	// Item

	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(ModBlocks.Furnace_Basic);
	}

	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.Furnace_Basic);
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityFurnaceBasic) {
				InventoryHelper.dropInventoryItems(world, pos, (TileEntityFurnaceBasic) tileentity);
				world.updateComparatorOutputLevel(pos, this);
			}
		

		super.breakBlock(world, pos, state);
	}

	// Miss

	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstone(world.getTileEntity(pos));
	}

}