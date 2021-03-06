package shane2482.deadlyworld.blocks.storage.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodCrate;

public class ContainerPlywoodCrate extends Container {

	private TileEntityPlywoodCrate Te;

	// Player Inventory
	private final int hotbar = 9;
	private final int playerInvRow = 3;
	private final int playerInvColumn = 9;
	private final int playerInv = playerInvRow + playerInvColumn;
	private final int playerSlots = hotbar + playerInv;

	// Slot Index
	private final int playerFirstSlotIndex = 0;
	private final int containerFirstSlotIndex = playerFirstSlotIndex + playerSlots;

	// Container Slot Count
	public final int containerSlots = 72;

	public ContainerPlywoodCrate(InventoryPlayer playerInv, TileEntityPlywoodCrate Te) {
		this.Te = Te;

		final int slotXSpacing = 18;
		final int slotYSpacing = 18;

		// Hotbar
		final int hotbar_Xpos = 35;
		final int hotbar_Ypos = 198;

		for (int x = 0; x < hotbar; ++x) {
			int slotNumber = x;
			addSlotToContainer(new Slot(playerInv, slotNumber, hotbar_Xpos + slotXSpacing * x, hotbar_Ypos));
		}

		// Player Inventory
		final int playerInv_Xpos = 35;
		final int playerInv_Ypos = 140;

		for (int y = 0; y < playerInvRow; ++y) {
			for (int x = 0; x < playerInvColumn; ++x) {
				int slotNumber = hotbar + y * playerInvColumn + x;
				int Xpos = playerInv_Xpos + x * slotXSpacing;
				int ypos = playerInv_Ypos + y * slotYSpacing;
				addSlotToContainer(new Slot(playerInv, slotNumber, Xpos, ypos));

			}
		}

		if (containerSlots != Te.getSizeInventory()) {
			System.err.println("Mismatched slot count in ContainerBasic(" + containerSlots + ") and TileInventory ("
					+ Te.getSizeInventory() + ")");
		}

		// Container Inventory
		final int containerXpos = 8;
		final int containerYpos = 18;

		for (int slotNumber = 0; slotNumber < containerSlots; slotNumber++) {
			addSlotToContainer(new Slot(Te, slotNumber, containerXpos + slotXSpacing * (slotNumber % 12),
					containerYpos + slotYSpacing * (slotNumber / 12)));
		}
	}

	@Nullable

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
		Slot sourceSlot = (Slot) inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack())
			return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceSlotIndex >= playerFirstSlotIndex && sourceSlotIndex < playerFirstSlotIndex + playerSlots) {
			if (!mergeItemStack(sourceStack, containerFirstSlotIndex, containerFirstSlotIndex + containerSlots,
					false)) {
				return null;
			}
		} else if (sourceSlotIndex >= containerFirstSlotIndex
				&& sourceSlotIndex < containerFirstSlotIndex + containerSlots) {
			if (!mergeItemStack(sourceStack, playerFirstSlotIndex, playerFirstSlotIndex + playerSlots, false)) {
				return null;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return null;
		}
		if (sourceStack.stackSize == 0) {
			sourceSlot.putStack(null);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.Te.closeInventory(playerIn);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.Te.isUseableByPlayer(player);
	}

}
