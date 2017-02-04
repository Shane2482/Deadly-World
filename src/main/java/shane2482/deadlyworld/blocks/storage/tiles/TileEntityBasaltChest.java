package shane2482.deadlyworld.blocks.storage.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import shane2482.deadlyworld.blocks.storage.block_basalt_chest;
import shane2482.deadlyworld.init.ModBlocks;

public class TileEntityBasaltChest extends TileEntityLockableLoot implements ITickable, IInventory {

	private ItemStack[] chestContents = new ItemStack[45];
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;
	private String customName;

	public TileEntityBasaltChest() {
	}

	// Size
	@Override
	public int getSizeInventory() {
		return 45;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	// Slots
	@Override
	public ItemStack getStackInSlot(int index) {
		return chestContents[index];
	}

	@Override
	public void markDirty() {
		super.markDirty();

	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

		ItemStack itemstack = ItemStackHelper.getAndSplit(chestContents, index, count);

		if (itemstack != null) {
			markDirty();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(chestContents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		chestContents[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	// Name
	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.basalt_chest";
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	public void setCustomName(String name) {
		customName = name;
	}

	@Override
	public String getGuiID() {
		return "Basalt_Chest";
	}

	@Override
	public ContainerChest createContainer(InventoryPlayer playerInv, EntityPlayer player) {

		return new ContainerChest(playerInv, this, player);
	}

	// NBT
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		chestContents = new ItemStack[getSizeInventory()];

		if (compound.hasKey("CustomName", 8)) {
			customName = compound.getString("CustomName");
		}

		if (!checkLootAndRead(compound)) {
			NBTTagList list = compound.getTagList("Items", 10);

			for (int i = 0; i < list.tagCount(); ++i) {
				NBTTagCompound compound1 = list.getCompoundTagAt(i);
				int j = compound1.getByte("Slot") & 255;

				if (j >= 0 && j < chestContents.length) {
					chestContents[j] = ItemStack.loadItemStackFromNBT(compound1);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!checkLootAndWrite(compound)) {
			NBTTagList list = new NBTTagList();

			for (int i = 0; i < chestContents.length; ++i) {
				if (chestContents[i] != null) {
					NBTTagCompound compound1 = new NBTTagCompound();
					compound1.setByte("Slot", (byte) i);
					chestContents[i].writeToNBT(compound1);
					list.appendTag(compound1);
				}
			}

			compound.setTag("Items", list);
		}

		if (hasCustomName()) {
			compound.setString("CustomName", customName);
		}

		return compound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(pos, metadata, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		return compound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound compound) {
		readFromNBT(compound);
	}

	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		return compound;
	}

	// Function
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) != this ? false
				: player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
	}

	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();

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
		if (!player.isSpectator() && getBlockType() instanceof block_basalt_chest) {
			--numPlayersUsing;
			worldObj.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);

		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		updateContainingBlockInfo();

	}

	// Update
	@Override
	public void update() {

		if (++ticksSinceSync % 20 * 4 == 0) {
			worldObj.addBlockEvent(pos, ModBlocks.Basalt_Chest, 1, numPlayersUsing);
		}

		prevLidAngle = lidAngle;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		float f = 0.1F;

		if (numPlayersUsing > 0 && lidAngle == 0.0F) {
			double d0 = (double) i + 0.5D;
			double d1 = (double) k + 0.5D;
			worldObj.playSound((EntityPlayer) null, d0, (double) j + 0.5D, d1, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
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
				worldObj.playSound((EntityPlayer) null, d3, (double) j + 0.5D, d2, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	// Miss
	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

		for (int i = 0; i < chestContents.length; ++i) {
			chestContents[i] = null;
		}
	}

	public static void registerFixesChest(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("Chest", new String[] { "Items" }));
	}

}
