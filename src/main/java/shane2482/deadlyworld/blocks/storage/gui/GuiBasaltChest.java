package shane2482.deadlyworld.blocks.storage.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import shane2482.deadlyworld.library.Reference;

public class GuiBasaltChest extends GuiContainer {

	private static final ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID + ":" + "textures/gui/container/Basalt_Chest_Gui.png");
	private final IInventory upperChestInventory;
	private final IInventory lowerChestInventory;

	private final int inventoryRows;

	public GuiBasaltChest(IInventory upperInv, IInventory lowerInv) {
		super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().thePlayer));
		upperChestInventory = upperInv;
		lowerChestInventory = lowerInv;
		allowUserInput = false;
		int i = 222;
		int j = 114;
		inventoryRows = lowerInv.getSizeInventory() / 9;
		ySize = 114 + inventoryRows * 18;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 14277081);
		fontRendererObj.drawString(upperChestInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2,
				14277081);
	}

	protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GUI);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		drawTexturedModalRect(i, j, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(i, j + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}
}