package shane2482.deadlyworld.blocks.storage;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.itemblocks;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodCrate;
import shane2482.deadlyworld.library.GuiHandler;

public class block_plywood_crate extends Block implements ITileEntityProvider {

	public block_plywood_crate(Material material, String name, String Regname, float hardness, float resistance, String tool, int level) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Regname);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		this.setCreativeTab(DeadlyWorld.block);
		this.register();
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
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	// Function
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPlywoodCrate();
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
		super.neighborChanged(state, world, pos, blockIn);
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileEntityPlywoodCrate) {
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
				player.openGui(DeadlyWorld.instance, GuiHandler.Plywood_Crate, world, pos.getX(), pos.getY(),
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

		if (!(tileentity instanceof TileEntityPlywoodCrate)) {
			return null;
		} else {
			ILockableContainer ilockablecontainer = (TileEntityPlywoodCrate) tileentity;

		}
		return getLockableContainer(world, pos);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity Te = world.getTileEntity(pos);

			if (Te instanceof TileEntityPlywoodCrate) {
				((TileEntityPlywoodCrate) Te).setCustomName(stack.getDisplayName());
			}
		}
	}

	// Behaviour
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstoneFromInventory(getLockableContainer(world, pos));
	}

}
