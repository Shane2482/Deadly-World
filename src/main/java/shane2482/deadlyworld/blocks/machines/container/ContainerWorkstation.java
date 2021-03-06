package shane2482.deadlyworld.blocks.machines.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityWorkstation;

public class ContainerWorkstation extends Container {
	private TileEntityWorkstation Te;

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
	public final int containerSlots = 24;

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();

	private World worldObj;

	public ContainerWorkstation(InventoryPlayer playerInv, TileEntityWorkstation Te) {
		this.Te = Te;

		final int slotXSpacing = 18;
		final int slotYSpacing = 18;

		// Output
				this.addSlotToContainer(new SlotCrafting(playerInv.player, craftMatrix, craftResult, 0, 201, 36));

				// Crafting Grid
				for (int y = 0; y < 3; ++y) {
					for (int x = 0; x < 3; ++x) {
						this.addSlotToContainer(new Slot(craftMatrix, x + y * 3, 107 + x * 18, 18 + y * 18));
					}
				}
				
		// Player Inventory
		final int playerInv_Xpos = 85;
		final int playerInv_Ypos = 77;

		for (int y = 0; y < playerInvRow; ++y) {
			for (int x = 0; x < playerInvColumn; ++x) {
				int slotNumber = hotbar + y * playerInvColumn + x;
				int Xpos = playerInv_Xpos + x * slotXSpacing;
				int ypos = playerInv_Ypos + y * slotYSpacing;
				addSlotToContainer(new Slot(playerInv, slotNumber, Xpos, ypos));

			}
		}

		// Hotbar
				final int hotbar_Xpos = 85;
				final int hotbar_Ypos = 135;

				for (int x = 0; x < hotbar; ++x) {
					int slotNumber = x;
					addSlotToContainer(new Slot(playerInv, slotNumber, hotbar_Xpos + slotXSpacing * x, hotbar_Ypos));
				}
				
				
				// Container Inventory
				final int containerXpos = 8;
				final int containerYpos = 27;

				for (int slotNumber = 0; slotNumber < containerSlots; slotNumber++) {
					addSlotToContainer(new Slot(Te, slotNumber, containerXpos + slotXSpacing * (slotNumber % 4),
							containerYpos + slotYSpacing * (slotNumber / 4)));
				}
				
		

		
		onCraftMatrixChanged(craftMatrix);
	}

	private void updateCraftingMatrix() {
    	for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
    	craftMatrix.setInventorySlotContents(i, Te.craftMatrixInventory[i]);
    	}
    	}

	 @Override
	  public void onCraftMatrixChanged(IInventory inventoryIn) {
	    this.craftResult
	        .setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	  }
    
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
    	super.onContainerClosed(par1EntityPlayer);
    	saveCraftingMatrix();
    }
    
    private void saveCraftingMatrix() {
    	for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
    		Te.craftMatrixInventory[i] = craftMatrix.getStackInSlot(i);
    	}
    }

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return Te.isUseableByPlayer(entityPlayer);
	}

	@Override
	@Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 10 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (index >= 37 && index < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            
           /* else if (index >= 47 && index < 61)
            {
                if (!this.mergeItemStack(itemstack1, 10, 47, false))
                {
                    return null;
                }
            }*/
            
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }



    public boolean canMergeSlot(ItemStack stack, Slot slot)
    {
        return slot.inventory != Te.craftResult && super.canMergeSlot(stack, slot);
    }
}