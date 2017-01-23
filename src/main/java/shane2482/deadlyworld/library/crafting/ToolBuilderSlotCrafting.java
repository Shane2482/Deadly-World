package shane2482.deadlyworld.library.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;

public class ToolBuilderSlotCrafting extends Slot {

	private final InventoryCrafting craftMatrix;
	private final EntityPlayer thePlayer;
	private int amountCrafted;

	public ToolBuilderSlotCrafting(EntityPlayer player, InventoryCrafting inventoryCraft, IInventory inventory,
			int slotIndex, int xPosition, int yPosition) {
		super(inventory, slotIndex, xPosition, yPosition);
		this.thePlayer = player;
		this.craftMatrix = inventoryCraft;
	}

	// Check if the stack is a valid item for this slot. Always true beside for
	// the armor slots.
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	// Decrease the size of the stack in slot (first int arg) by the amount of
	// the second int arg. Returns the new stack.
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(amount, this.getStack().stackSize);
		}

		return super.decrStackSize(amount);
	}

	// The itemStack passed in is the output - ie, iron ingots, and pickaxes,
	// not ore and wood. Typically increases an internal count then calls
	// onCrafting(item).
	protected void onCrafting(ItemStack stack, int amount) {
		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	// The itemStack passed in is the output - ie, iron ingots, and pickaxes,
	// not ore and wood.
	protected void onCrafting(ItemStack stack) {
		if (this.amountCrafted > 0) {
			stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
		}

		this.amountCrafted = 0;
	}

	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
		this.onCrafting(stack);
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
		ItemStack[] aitemstack = ToolBuilderCraftingManager.getInstance().getRemainingItems(this.craftMatrix,
				player.worldObj);
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
			ItemStack itemstack1 = aitemstack[i];

			if (itemstack != null) {
				this.craftMatrix.decrStackSize(i, 1);
			}

			if (itemstack1 != null) {
				if (this.craftMatrix.getStackInSlot(i) == null) {
					this.craftMatrix.setInventorySlotContents(i, itemstack1);
				} else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1)) {
					this.thePlayer.dropItem(itemstack1, false);
				}
			}
		}
	}
}