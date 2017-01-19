package shane2482.deadlyworld.blocks.machines.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shane2482.deadlyworld.init.ModBlocks;
import shane2482.deadlyworld.tiles.TileEntityWorkstation;

public class ContainerToolBuilder extends Container {
	// Player Inventory
	private final int hotbar = 9;
	private final int playervRow = 3;
	private final int playervColumn = 9;
	private final int playerv = playervRow + playervColumn;
	private final int playerSlots = hotbar + playerv;

	// Slot Index
	private final int playerFirstSlotIndex = 0;

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();

	private World worldObj;
	private BlockPos pos;

	public ContainerToolBuilder(InventoryPlayer playerInv, World world, BlockPos posIn) {
		this.worldObj = world;
        this.pos = posIn;
		final int slotXSpacing = 18;
		final int slotYSpacing = 18;

		// Hotbar
		final int hotbar_Xpos = 8;
		final int hotbar_Ypos = 142;

		for (int x = 0; x < hotbar; ++x) {
			int slotNumber = x;
			addSlotToContainer(new Slot(playerInv, slotNumber, hotbar_Xpos + slotXSpacing * x, hotbar_Ypos));
		}

		// Player Inventory
		final int playerv_Xpos = 8;
		final int playerv_Ypos = 84;

		for (int y = 0; y < playervRow; ++y) {
			for (int x = 0; x < playervColumn; ++x) {
				int slotNumber = hotbar + y * playervColumn + x;
				int Xpos = playerv_Xpos + x * slotXSpacing;
				int ypos = playerv_Ypos + y * slotYSpacing;
				addSlotToContainer(new Slot(playerInv, slotNumber, Xpos, ypos));

			}
		}

		// Output
		this.addSlotToContainer(new SlotCrafting(playerInv.player, craftMatrix, craftResult, 0, 123, 34));

		// Crafting Grid
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

		onCraftMatrixChanged(craftMatrix);
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }

	@Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if (itemstack != null)
                {
                    player.dropItem(itemstack, false);
                }
            }
        }
    }


	@Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.worldObj.getBlockState(this.pos).getBlock() != ModBlocks.Tool_Builder ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
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

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}