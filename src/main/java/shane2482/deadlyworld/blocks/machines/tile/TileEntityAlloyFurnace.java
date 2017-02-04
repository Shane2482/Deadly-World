package shane2482.deadlyworld.blocks.machines.tile;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.blocks.machines.block_alloy_furnace;
import shane2482.deadlyworld.init.ModBlocks;
import shane2482.deadlyworld.library.ModSoundHandler;
import shane2482.deadlyworld.library.crafting.AlloyRecipes;

public class TileEntityAlloyFurnace extends TileEntity implements ISidedInventory, ITickable {

	private ItemStack slots[];

	private static final int[] SLOTS_TOP = new int[] { 0, 1 };
	private static final int[] SLOTS_BOTTOM = new int[] { 3, 2 };
	private static final int[] SLOTS_SIDES = new int[] { 2 };

	private String customName;

	public int cookTime;
	public int power;
	public static final int maxPower = 102401;
	public static final int alloySpeed = 100;

	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	public TileEntityAlloyFurnace() {
		slots = new ItemStack[4];
	}

	// Name
	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.alloy_furnace";
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	public void setCustomName(String name) {
		customName = name;
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	@Nullable
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(slots, index, count);
	}

	@Nullable
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(slots, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		slots[index] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) != this ? false
				: player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 3) {
			return false;
		} else if (index != 2) {
			return true;
		} else {
			ItemStack itemstack = this.slots[2];
			return hasItemPower(stack)
					|| SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
		}
	}

	public static int getItemPower(ItemStack stack) {
		if (stack == null) {
			return 0;
		} else {
			Item item = stack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB) {
					return 150;
				}

				if (block.getDefaultState().getMaterial() == Material.WOOD) {
					return 300;
				}

				if (block == Blocks.COAL_BLOCK) {
					return 16000;
				}
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName()))
				return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName()))
				return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName()))
				return 200;
			if (item == Items.STICK)
				return 100;
			if (item == Items.COAL)
				return 1600;
			if (item == Items.LAVA_BUCKET)
				return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING))
				return 100;
			if (item == Items.BLAZE_ROD)
				return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
		}
	}

	public static boolean hasItemPower(ItemStack stack) {

		return getItemPower(stack) > 0;
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.cookTime;
		case 1:
			return this.power;

		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.cookTime = value;
			break;
		case 1:
			this.power = value;

		}
	}

	public int getFieldCount() {
		return 2;
	}

	public void clear() {
		for (int i = 0; i < this.slots.length; ++i) {
			this.slots[i] = null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < slots.length) {
				slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		power = compound.getShort("PowerTime");
		cookTime = compound.getShort("CookTime");

		if (compound.hasKey("CustomName", 8)) {
			customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("PowerTime", (short) power);
		compound.setInteger("CookTime", (short) cookTime);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < slots.length; ++i) {
			if (slots[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				slots[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (hasCustomName()) {
			compound.setString("CustomName", customName);
		}

		return compound;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int invSize = this.getSizeInventory();
		if (invSize > 0) {
			int[] theInt = new int[invSize];
			for (int i = 0; i < theInt.length; i++) {
				theInt[i] = i;
			}
			return theInt;
		} else {
			return new int[0];
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 2) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

	private boolean canAlloy() {
		if (slots[0] == null || slots[1] == null) {
			return false;
		}

		ItemStack stack = AlloyRecipes.getAlloyResult(slots[0].getItem(), slots[1].getItem());

		if (stack == null) {
			return false;
		}

		if (slots[3] == null) {
			return true;
		}

		if (!slots[3].isItemEqual(stack)) {
			return false;
		}

		if (slots[3].stackSize < getInventoryStackLimit() && slots[3].stackSize < slots[3].getMaxStackSize()) {
			return true;
		} else {
			return slots[3].stackSize < stack.getMaxStackSize();
		}

	}

	private void alloyItems() {

		if (canAlloy()) {
			ItemStack stack = AlloyRecipes.getAlloyResult(slots[0].getItem(), slots[1].getItem());

			if (slots[3] == null) {
				slots[3] = stack.copy();
			} else if (slots[3].isItemEqual(stack)) {
				slots[3].stackSize += stack.stackSize;
			}

			for (int i = 0; i < 2; ++i) {
				if (slots[i].stackSize <= 0) {
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				} else {
					slots[i].stackSize--;
				}

				if (slots[i].stackSize <= 0) {
					slots[i] = null;
				}
			}
		}

	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			if (numPlayersUsing < 0) {
				numPlayersUsing = 0;
			}

			++numPlayersUsing;
			worldObj.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);

		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && getBlockType() instanceof block_alloy_furnace) {
			--numPlayersUsing;
			worldObj.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);

		}
	}

	// Update
	@Override
	public void update() {

		boolean flag = hasPower();
		boolean flag1 = false;

		if (hasPower() && isSmelting()) {
			power--;
		}

		if (!worldObj.isRemote) {
			if (hasItemPower(slots[2]) && power < (maxPower - getItemPower(slots[2]))) {
				power += getItemPower(slots[2]);

				if (slots[2] != null) {
					flag1 = true;
					slots[2].stackSize--;

					if (slots[2].stackSize == 0) {
						slots[2] = slots[2].getItem().getContainerItem(slots[2]);
					}
				}
			}

			if (hasPower() && canAlloy()) {
				cookTime++;

				if (cookTime == alloySpeed) {
					cookTime = 0;
					alloyItems();
					flag1 = true;
				}
			} else {
				cookTime = 0;
			}

			if (flag != isSmelting()) {
				flag1 = true;
				IBlockState state = worldObj.getBlockState(pos);
				worldObj.notifyBlockUpdate(pos, state, state, 3);

			}

		}

		if (flag1) {
			markDirty();
		}
		if (++ticksSinceSync % 20 * 4 == 0) {
			worldObj.addBlockEvent(pos, ModBlocks.AlloyFurnace, 1, numPlayersUsing);
		}

		prevLidAngle = lidAngle;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		float f = 0.1F;

		if (numPlayersUsing > 0 && lidAngle == 0.0F) {
			double d0 = (double) i + 0.5D;
			double d1 = (double) k + 0.5D;
			worldObj.playSound((EntityPlayer) null, d0, (double) j + 0.5D, d1, ModSoundHandler.furnace_open,
					SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F) {
			float f2 = lidAngle;

			if (numPlayersUsing > 0) {
				lidAngle += 0.1F;
			} else {
				lidAngle -= 0.1F;
			}

			if (lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}

			float f1 = 0.5F;

			if (lidAngle < 0.5F && f2 >= 0.5F) {
				double d3 = (double) i + 0.5D;
				double d2 = (double) k + 0.5D;
				worldObj.playSound((EntityPlayer) null, d3, (double) j + 0.5D, d2, ModSoundHandler.furnace_close,
						SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	public boolean isSmelting() {
		return cookTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isSmelting(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public boolean hasPower() {
		return power > 0;
	}

}
