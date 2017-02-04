package shane2482.deadlyworld.blocks.machines.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityFurnaceBasic;
import shane2482.deadlyworld.library.Utils;

public class ContainerFurnaceBasic extends Container {
	private final TileEntityFurnaceBasic Te;
	private int cookTime;
	private int power;

	public ContainerFurnaceBasic(InventoryPlayer playerInventory, TileEntityFurnaceBasic furnaceInventory,
			EntityPlayer player) {
		this.Te = furnaceInventory;

		furnaceInventory.openInventory(player);

		// Input
		addSlotToContainer(new Slot(furnaceInventory, 0, 76, 31));
		// Fuel
		addSlotToContainer(new Slot(furnaceInventory, 1, 14, 53));
		// Output
		addSlotToContainer(new SlotBasaltFurnace(playerInventory.player, furnaceInventory, 2, 134, 33));

		// Player Inv
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Hotbar
		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.Te.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		int inventoryStart = 4;
		int inventoryEnd = inventoryStart + 26;
		int hotbarStart = inventoryEnd + 1;
		int hotbarEnd = hotbarStart + 8;

		Slot theSlot = this.inventorySlots.get(slot);

		if (theSlot != null && theSlot.getHasStack()) {
			ItemStack newStack = theSlot.getStack();
			ItemStack currentStack = newStack.copy();

			// Slots in Inventory to shift from
			if (slot == 3 || slot == 3) {
				if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
					return null;
				}
				theSlot.onSlotChange(newStack, currentStack);
			}
			// Other Slots in Inventory excluded
			else if (slot >= inventoryStart) {
				// Shift from Inventory
				if (Utils.isValid(FurnaceRecipes.instance().getSmeltingResult(newStack))) {
					if (!this.mergeItemStack(newStack, 0, 0 + 1, false)) {
						if (!this.mergeItemStack(newStack, 1, 1 + 1, false)) {
							return null;
						}
					}
				}
				//

				else if (slot >= inventoryStart && slot <= inventoryEnd) {
					if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
						return null;
					}
				} else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1
						&& !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
				return null;
			}

			if (Utils.isValid(newStack)) {
				theSlot.putStack(null);
			} else {
				theSlot.onSlotChanged();
			}

			if (Utils.getStackSize(newStack) == Utils.getStackSize(currentStack)) {
				return null;
			}
			theSlot.onPickupFromSlot(player, newStack);

			return currentStack;
		}
		return null;
	}

	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.Te.closeInventory(playerIn);
	}

	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, Te);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);

			if (this.cookTime != this.Te.getField(0)) {
				icontainerlistener.sendProgressBarUpdate(this, 0, this.Te.getField(0));
			}

			if (this.power != this.Te.getField(1)) {
				icontainerlistener.sendProgressBarUpdate(this, 1, this.Te.getField(1));
			}

		}

		this.cookTime = this.Te.getField(0);
		this.power = this.Te.getField(1);

	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.Te.setField(id, data);
	}

}