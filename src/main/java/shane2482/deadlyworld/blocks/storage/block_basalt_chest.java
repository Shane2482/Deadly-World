package shane2482.deadlyworld.blocks.storage;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.itemblocks;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityBasaltChest;
import shane2482.deadlyworld.library.GuiHandler;

public class block_basalt_chest extends BlockContainer {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.875D,	0.9375D);
	protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 1.0D);
	protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
	protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 1.0D, 0.875D, 0.9375D);
	protected static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

	public block_basalt_chest(Material material, String name, String Regname, float hardness, float resistance, String tool, int level) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Regname);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(DeadlyWorld.block);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		register();

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

	// Rendering
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return source.getBlockState(pos.north()).getBlock() == this ? NORTH_CHEST_AABB
				: (source.getBlockState(pos.south()).getBlock() == this ? SOUTH_CHEST_AABB
						: (source.getBlockState(pos.west()).getBlock() == this ? WEST_CHEST_AABB
								: (source.getBlockState(pos.east()).getBlock() == this ? EAST_CHEST_AABB
										: NOT_CONNECTED_AABB)));
	}

	// Facing
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.offset(enumfacing);
			IBlockState iblockstate = world.getBlockState(blockpos);

		}
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		EnumFacing enumfacing = EnumFacing
				.getHorizontal(MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3)
				.getOpposite();
		state = state.withProperty(FACING, enumfacing);
		BlockPos blockpos = pos.north();
		BlockPos blockpos1 = pos.south();
		BlockPos blockpos2 = pos.west();
		BlockPos blockpos3 = pos.east();
		boolean flag = this == world.getBlockState(blockpos).getBlock();
		boolean flag1 = this == world.getBlockState(blockpos1).getBlock();
		boolean flag2 = this == world.getBlockState(blockpos2).getBlock();
		boolean flag3 = this == world.getBlockState(blockpos3).getBlock();

		if (!flag && !flag1 && !flag2 && !flag3) {
			world.setBlockState(pos, state, 3);
		} else if (enumfacing.getAxis() != EnumFacing.Axis.X || !flag && !flag1) {
			if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3)) {
				if (flag2) {
					world.setBlockState(blockpos2, state, 3);
				} else {
					world.setBlockState(blockpos3, state, 3);
				}

				world.setBlockState(pos, state, 3);
			}
		} else {
			if (flag) {
				world.setBlockState(blockpos, state, 3);
			} else {
				world.setBlockState(blockpos1, state, 3);
			}

			world.setBlockState(pos, state, 3);
		}

		if (stack.hasDisplayName()) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityBasaltChest) {
				((TileEntityBasaltChest) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	public IBlockState correctFacing(World world, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = null;

		for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
			IBlockState iblockstate = world.getBlockState(pos.offset(enumfacing1));

			if (iblockstate.getBlock() == this) {
				return state;
			}

			if (iblockstate.isFullBlock()) {
				if (enumfacing != null) {
					enumfacing = null;
					break;
				}

				enumfacing = enumfacing1;
			}
		}

		if (enumfacing != null) {
			return state.withProperty(FACING, enumfacing.getOpposite());
		} else {
			EnumFacing enumfacing2 = (EnumFacing) state.getValue(FACING);

			if (world.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
				enumfacing2 = enumfacing2.getOpposite();
			}

			if (world.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
				enumfacing2 = enumfacing2.rotateY();
			}

			if (world.getBlockState(pos.offset(enumfacing2)).isFullBlock()) {
				enumfacing2 = enumfacing2.getOpposite();
			}

			return state.withProperty(FACING, enumfacing2);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	// Function
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBasaltChest();
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
		super.neighborChanged(state, world, pos, blockIn);
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileEntityBasaltChest) {
			tileentity.updateContainingBlockInfo();
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileentity);
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			ILockableContainer ilockablecontainer = getLockableContainer(world, pos);

			if (ilockablecontainer != null && !player.isSneaking()) {
				player.openGui(DeadlyWorld.instance, GuiHandler.Basalt_Chest, world, pos.getX(), pos.getY(),
						pos.getZ());

			}

			return true;
		}
	}

	// Lockable Container
	@Nullable
	public ILockableContainer getLockableContainer(World world, BlockPos pos) {
		return getContainer(world, pos, false);
	}

	@Nullable
	public ILockableContainer getContainer(World world, BlockPos pos, boolean bool) {
		TileEntity tileentity = world.getTileEntity(pos);

		if (!(tileentity instanceof TileEntityBasaltChest)) {
			return null;
		} else {
			ILockableContainer ilockablecontainer = (TileEntityBasaltChest) tileentity;

			if (!bool && isBlocked(world, pos)) {
				return null;
			} else {
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
					BlockPos blockpos = pos.offset(enumfacing);
					Block block = world.getBlockState(blockpos).getBlock();

					if (block == this) {
						if (isBlocked(world, blockpos)) {
							return null;
						}

						TileEntity tileentity1 = world.getTileEntity(blockpos);

					}
				}

				return ilockablecontainer;
			}
		}
	}

	// Behaviour
	private boolean isBlocked(World world, BlockPos pos) {
		return isBelowSolidBlock(world, pos) || isOcelotSittingOnChest(world, pos);
	}

	private boolean isBelowSolidBlock(World world, BlockPos pos) {
		return world.getBlockState(pos.up()).isSideSolid(world, pos.up(), EnumFacing.DOWN);
	}

	private boolean isOcelotSittingOnChest(World world, BlockPos pos) {
		for (Entity entity : world.getEntitiesWithinAABB(EntityOcelot.class,
				new AxisAlignedBB((double) pos.getX(), (double) (pos.getY() + 1), (double) pos.getZ(),
						(double) (pos.getX() + 1), (double) (pos.getY() + 2), (double) (pos.getZ() + 1)))) {
			EntityOcelot entityocelot = (EntityOcelot) entity;

			if (entityocelot.isSitting()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstoneFromInventory(getLockableContainer(world, pos));
	}

}
